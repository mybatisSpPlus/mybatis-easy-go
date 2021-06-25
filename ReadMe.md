# mybatis-sp-plus
让开发者可以用接近书写sql的方式构建各种查询。并且方便的处理类型的转换，分页等工作。并且可以在切换源数据库后可以自动适配

## 使用方式

### 使用前准备
在spring boot程序中
增加扫描
``` java
  @SpringBootApplication
  @ComponentScan("com.github.mybatis.sp.plus.spring")
  @MapperScan("com.github.mybatis.sp.plus.spring")
  public class SpingBootAppBizApplication{
    public static void main(String[] args) {
        SpringApplication.run(SpingBootAppBizApplication.class, args);
    }
  }
```
使用时引入下面依赖
``` java
import static com.github.mybatis.sp.plus.ActionMethods.*;
import static com.github.mybatis.sp.plus.ConditionMethods.*;
import static com.github.mybatis.sp.plus.FunctionMethods.*;
import static com.github.mybatis.sp.plus.MetaMethods.*;
```

### 开始使用

#### 查询
``` java
// 返回查询结果集
public List<Map<String,Object>> selectListMapDemo(){
    // sql: select field f1,fieldf2 from table t1 where fid=1
    List<Map<String,Object>> result=select("field f1","field f2").from("table t1").where(field("fid").eq(1)).executeSelect().getResultList();
    return result;
}
// 将返回结构转为指定的对象
public List<MyObject> selectListObjectDemo(){
    // sql: select field f1,fieldf2 from table t1 where fid=1
    List<MyObject> result=select("field f1","field f2").from("table t1").where(field("fid").eq(1)).executeListSelect(MyObject.class);
    return result;
}
// 进行分页查询
public PageRecord<MyObject> selectPageObjectDemo(){
    // sql: select field f1,fieldf2 from table t1 where fid=1 limit 10,offset 0
    PageRecord<MyObject> result=select("field f1","field f2").from("table t1").where(field("fid").eq(1)).executePageSelect(1,10,MyObject.class);
    return result;
}
// 使用函数
public int selectCountDemo(){
    // select count(1) from table
    Long count=select(count(allField())).from("table").executeOneSelect(Long.class);
    return count;
}
// 进行关联查询
public List<Map<String,Object>> selectWithJoinDemo(){
    // select * from table t1 left join table t2 on t1.id=t2.id where t1.name like '%name%'
    List<Map<String,Object>> result=select(allField()).from("table").as("t1").leftJoin("table t2").on(field("t1.id").eq(fileldd("t2.id"))).where(field("t1.name").like("name"))
    .executeSelect().getResultList();
    return result;
}

// 子查询
public List<String> subQueryDemo(）{
    // select name from table1 where id in(select id from table2)
    List<String> result=select("name").from("table1").where(field("id").in(select("id").from("table2")).asTable()).executeListSelect(String.class);
    return result;
}
```
#### 插入

``` java
// 插入数据1
public int insertDemo(){
    // sql: insert into tableA(f1,f2) value(1,2),(3,4)	
    return insertInto("tableA").fields("f1","f2").values(Arrays.asList(1,2),Arrays.asList(3,4)).executeInsert();
 }
// 插入数据2
public int insertSelectDemo(){
    // sql: insert into tableA select * from tableB
    return insertInto("tableA").select(allField()).from("tableB").executeInsert();
}
```

#### 更新

``` java
// 更新数据
public int updateDemo(){
    // sql: update table1 set field1=1,field2=2
    update("table1").set().setFieldValue(field("field1"),1).setFieldValue(field("field2"),2).executeUpdate();
}
// 联表更新
public int updateWithJoinDemo(){
    // sql: update table1 t1 left join table2 t2  on t1.field1=t2.field1 set t1.field2=t2.field2
    update("table1 t1")leftJoin("table2 t2").on(field("t1.field1").on("t2.field1")).set().setFieldValue(field("t1.field2"),field("t2.field2")).executeUpdate();
}
```

#### 删除

``` java
 // 删除数据
 public int deleteDemo(){
    // sql: delete from table1 where id>1
    return delete().from("table1").where(field(id).gt(1)).executeDelete();
 }
 
 // 联表删除
 public int deleteWithJoinDemo(){
    // sql: delete t1,t2 from table1 t1 left join table2 t2 on t1.id=t2.id where t1.id>20
    return delete("t1","t2").from("table1 t1").leftJoin("table2 t2").on(field("t1.id").eq(field("t2.id"))).where(field("t1.id").gt(20)).executeDelete();
 }
```

#### 基于注解的针对单表的简单操作

``` java
//类对应的表名为Test,schema为空
@TABLE(tableName="Test")
public class TestA {
    //属性对应的数据表字段为Idx，且是表的主键。类型转换时会尝试将
    //数据库类型转为对应的java类型。
    @ID
    @FIELD(fieldName = "Idx")
    private String id;
    //属性对应的数据表字段为name。
    @FIELD(fieldName = "name")
    private String val;
}
//批量插入
public void insertClassDemo() throws Exception {
    TestA a=new TestA("123","name1");
    TestA b=new TestA("124","name2");
    TestA c=new TestA("125","name3");
    TestA d=new TestA("126","name4");
    insertInto(a,b,c,d);
}
//根据主键删除
public void deleteClassDemo() throws Exception {
    deleteOne(TestA.class,"123");
}
//根据条件删除
public void deleteClassDemo2() throws Exception {
    deleteList(TestA.class,field("Idx").in("1243","1223"));
}
//更新对象属性，可以指定是否设置空值
public void updateClassDemo() throws Exception {
    TestA d=new TestA("124","nameX");
    update(d,false);
    TestA a=new TestA("123",null);
    update(a,true);
}
//根据主键查询对象
public void selectClassDemo() throws Exception {
    TestA a=selectOne(TestA.class,"124");
    System.out.println(a.toString());
}
//根据类型查询全部
public void selectClassDemo2() throws Exception {
    List<TestA> list=selectList(TestA.class);
}
//根据类型查询，并添加排序
public void selectClassDemo3() throws Exception {
    List<TestA> list=selectList(TestA.class,order("Idx",true));
}

//根据类型查询，并添加排序
public void selectClassDemo4() throws Exception {
    List<TestA> list=selectList(TestA.class,order("Idx",true));
}

//根据类型查询，并添加排序,且只返回前10条记录
public void selectClassDemo5() throws Exception {
    List<TestA> list=selectList(TestA.class,10,order("Idx",true));
}

//根据类型查询，并添加排序和过滤条件,且只返回前10条记录
public void selectClassDemo6() throws Exception {
    List<TestA> list=selectList(TestA.class,field("Idx").in("1243","1223"),10,order("Idx",true));
}

//根据类型分页查询，并排序
public void selectClassDemo7() throws Exception {
    PageRecord<TestA> list=selectPage(TestA.class,1,2,order("Idx",true));
}
//根据类型分页查询，并添加过滤条件
public void selectClassDemo8() throws Exception {
    PageRecord<TestA> list=selectPage(TestA.class,field("name").isNull(),1,2);
}

```

### 部分使用说明

在使用表，字段的地方，可以传入字符串，传入后会自动的拆分成表名和别名

表名支持的格式：tableName [alias]

字段名支持的格式：[spPrefix@] [tableName.]fieldName [as] [alias] [@spPostfix] 前缀和后缀会原样出现在sql中，对于部分数据库的特殊使用方式可以使用这种方式来实现 比如mysql的
BINARY 关键字

常量字段需要使用：  constantField(value)

空值需要使用：nullValue();

使用子查询时，需要在子查询的最后调用 asTable()，转为table对象

在所有需要传入 Object 的地方都可以传入Field对象，和Table对象
