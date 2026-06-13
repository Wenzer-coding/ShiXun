import os

# 定义9个模块的配置
modules = [
    {
        'name': 'TrainProject',
        'table': 'train_project',
        'fields': ['id', 'project_name', 'project_code', 'project_type', 'train_cycle', 'credit', 'cost', 'content', 'target', 'limit_major', 'status', 'create_user', 'create_time', 'update_time'],
        'joins': '',
        'search_fields': ['project_name', 'project_code', 'project_type', 'status']
    },
    {
        'name': 'TrainPlan',
        'table': 'train_plan',
        'fields': ['id', 'project_id', 'plan_name', 'term_year', 'start_date', 'end_date', 'train_address', 'teacher_id', 'second_teacher_id', 'max_student', 'enroll_num', 'enroll_start', 'enroll_end', 'plan_status', 'remark', 'create_time', 'update_time', 'project_name', 'teacher_name'],
        'joins': 'LEFT JOIN train_project p ON t.project_id = p.id LEFT JOIN sys_user u ON t.teacher_id = u.id',
        'search_fields': ['plan_name', 'term_year', 'plan_status']
    },
    {
        'name': 'TrainEnroll',
        'table': 'train_enroll',
        'fields': ['id', 'plan_id', 'stu_id', 'enroll_time', 'audit_status', 'audit_user', 'audit_time', 'audit_remark', 'create_time', 'update_time', 'plan_name', 'student_name', 'student_no'],
        'joins': 'LEFT JOIN train_plan p ON e.plan_id = p.id LEFT JOIN student s ON e.stu_id = s.id',
        'search_fields': ['audit_status', 'plan_id']
    },
    {
        'name': 'TrainAttendance',
        'table': 'train_attendance',
        'fields': ['id', 'plan_id', 'stu_id', 'attendDate', 'sign_time', 'sign_status', 'leave_remark', 'create_user', 'create_time', 'update_time', 'plan_name', 'student_name', 'student_no'],
        'joins': 'LEFT JOIN train_plan p ON a.plan_id = p.id LEFT JOIN student s ON a.stu_id = s.id',
        'search_fields': ['plan_id', 'sign_status', 'attendDate']
    },
    {
        'name': 'TrainMaterial',
        'table': 'train_material',
        'fields': ['id', 'material_name', 'spec', 'unit', 'price', 'stock_num', 'warn_num', 'remark', 'status', 'create_time', 'update_time'],
        'joins': '',
        'search_fields': ['material_name', 'status']
    },
    {
        'name': 'TrainMaterialUse',
        'table': 'train_material_use',
        'fields': ['id', 'plan_id', 'material_id', 'use_num', 'use_user', 'use_date', 'use_remark', 'create_time', 'plan_name', 'material_name', 'use_user_name'],
        'joins': 'LEFT JOIN train_plan p ON mu.plan_id = p.id LEFT JOIN train_material m ON mu.material_id = m.id LEFT JOIN sys_user u ON mu.use_user = u.id',
        'search_fields': ['plan_id', 'material_id']
    },
    {
        'name': 'TrainScore',
        'table': 'train_score',
        'fields': ['id', 'plan_id', 'stu_id', 'usual_score', 'report_score', 'defense_score', 'total_score', 'grade', 'score_remark', 'score_teacher', 'score_time', 'create_time', 'update_time', 'plan_name', 'student_name', 'student_no'],
        'joins': 'LEFT JOIN train_plan p ON s.plan_id = p.id LEFT JOIN student st ON s.stu_id = st.id',
        'search_fields': ['plan_id', 'grade']
    }
]

template = '''<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.eutmp.app.mapper.{name}Mapper">
    
    <resultMap id="BaseResultMap" type="com.eutmp.app.bean.{name}">
{result_map}
    </resultMap>
    
    <sql id="Base_Column_List">
{column_list}
    </sql>
    
    <select id="selectById" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List"/> FROM {table} t {joins} WHERE t.id = #{{id}}
    </select>
    
    <select id="selectAll" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List"/> FROM {table} t {joins}
        <where>
{search_conditions}
        </where>
        ORDER BY t.create_time DESC
    </select>
    
    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO {table} ({insert_fields}) VALUES ({insert_values})
    </insert>
    
    <update id="update">
        UPDATE {table}
        <set>
{update_fields}
        </set>
        WHERE id = #{{id}}
    </update>
    
    <delete id="deleteById">DELETE FROM {table} WHERE id = #{{id}}</delete>
    
    <delete id="deleteByIds">
        DELETE FROM {table} WHERE id IN
        <foreach item="id" collection="ids" open="(" separator="," close=")">#{{id}}</foreach>
    </delete>
{unique_check}
</mapper>
'''

base_path = r'D:\javacode\eutmp\src\main\resources\mapper'

for mod in modules:
    name = mod['name']
    table = mod['table']
    fields = mod['fields']
    joins = mod['joins']
    search_fields = mod['search_fields']
    
    # 生成resultMap
    result_map = '\n'.join([f'        <result column="{f}" property="{"".join(x.capitalize() if i > 0 else x for i, x in enumerate(f.split("_")))}"/>' for f in fields if f not in ['id']])
    
    # 生成column list
    column_list = ', '.join([f't.{f}' if not f.endswith('_name') and not f.endswith('_no') else f'{f}' for f in fields])
    
    # 生成insert fields
    insert_fields = ', '.join([f for f in fields if f not in ['id', 'create_time', 'update_time', 'project_name', 'teacher_name', 'plan_name', 'student_name', 'student_no', 'material_name', 'use_user_name']])
    insert_values = ', '.join([f'#{{{"".join(x.capitalize() if i > 0 else x for i, x in enumerate(f.split("_")))}}}' for f in fields if f not in ['id', 'create_time', 'update_time', 'project_name', 'teacher_name', 'plan_name', 'student_name', 'student_no', 'material_name', 'use_user_name']])
    
    # 生成update fields
    update_fields = '\n'.join([f'            <if test="{"".join(x.capitalize() if i > 0 else x for i, x in enumerate(f.split("_")))} != null">{f} = #{{{"".join(x.capitalize() if i > 0 else x for i, x in enumerate(f.split("_")))}}},</if>' for f in fields if f not in ['id', 'create_time', 'update_time', 'project_name', 'teacher_name', 'plan_name', 'student_name', 'student_no', 'material_name', 'use_user_name']])
    
    # 生成search conditions
    search_conditions = '\n'.join([f'            <if test="{field} != null{" and " + field + " != \\'\\'" if 'name' in field or 'code' in field else ""}">{search_condition(field)}</if>' for field in search_fields])
    
    # 生成unique check
    unique_check = ''
    if 'code' in fields:
        code_field = [f for f in fields if 'code' in f][0]
        property_name = "".join(x.capitalize() if i > 0 else x for i, x in enumerate(code_field.split("_")))
        unique_check = f'''
    <select id="checkProjectCodeUnique" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List"/> FROM {table} WHERE {code_field} = #{{{property_name}}} LIMIT 1
    </select>'''
    
    content = template.format(
        name=name,
        table=table,
        result_map=result_map,
        column_list=column_list,
        joins=joins,
        search_conditions=search_conditions,
        insert_fields=insert_fields,
        insert_values=insert_values,
        update_fields=update_fields,
        unique_check=unique_check
    )
    
    with open(os.path.join(base_path, f'{name}Mapper.xml'), 'w', encoding='utf-8') as f:
        f.write(content)
    print(f'Created {name}Mapper.xml')

def search_condition(field):
    if 'name' in field or 'code' in field:
        return f'AND t.{field} LIKE CONCAT(\'%\', #{{{field}}}, \'%\')'
    else:
        return f'AND t.{field} = #{{{field}}}'

print('All XML files generated successfully!')
