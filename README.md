# 脱敏组件

## 该组件适用以下场景：

1. Dubbo服务提供方，期望对结果集中某些敏感数据进行脱敏处理。
2. 直接对指定结果集中某些敏感数据进行脱敏处理。


## 快速上手
### 在Dubbo中启用脱敏组件

1. 在项目pom.xml配置文件中引入以下内容（版本可能发生迭代，到仓库确认引用最新版本）

```java
<dependency>  
   <groupId>org.rhine</groupId>  
   <artifactId>datamasking</artifactId>  
   <version>1.0.0-RELEASE</version>  
 </dependency>
```

2. 对需要脱敏字段添加注解

- 在定义接口方法中增加`@Sensitive`注解 
- 在接口返回结果集中需要脱敏的字段上增加`@Sensitive`注解

### 直接脱敏结果集的数据

1. 引入jar包
2. 对结果集中的字段添加`@Sensitive`注解
3. 显示调用`DataMaskingUtils.doMasking(Class<?> clazz, Object value)`,将结果集Class类型及结果传入即可。

## 扩展接口

考虑到业务场景的多样性，业务方可以根据业务场景控制单个字段是否脱敏，接口定义如下：

```java
public interface DataMaskingConditionSPI {

  boolean needMasking(MaskingContext context);

}

public class MaskingContext {

   /**
    * 传递线程上下文信息
    */
   ThreadLocal<Map<Object, Object>> threadLocalWithMap = new ThreadLocal<>();

   /**
    * 当前执行脱敏的方式
    */
   private Method method;

   /**
    * 当前执行脱敏的字段
    */
   private Field field;

   /**
    * 当前执行脱敏字段的注解
    */
   private Sensitive sensitive;

   /**
    * 当前脱敏字段的值
    */
   private Object value;

   /**
    * 当前脱敏字段类型
    */
   private Class<?> fieldParameterClazz;

}
```
组件在对每个字段脱敏之前都会显示调用`needMasking`方法，获取当前字段是否脱敏。（自动拓展实现时请注意性能问题，否则会拖慢脱敏组件执行效率）。





 