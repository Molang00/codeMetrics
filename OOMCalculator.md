# Object-Oriented Design Metrics (OOM)

## Overall Status

There are **14 metrics** we calculated in total, which can be divided to 3 main parts by the inventor of these metrics: **Chidamber and Kemerer Metrics**, **Lorenz and Kidd Metrics**, and **Abreu Metrics**. 

These metrics can also be understood in the abstract level they describe: **System level**, **Class level**, **and method level**. 

Some metrics are used to describe methods and the other ones are used to describe attributes. 

To get more details of these metrics, please reference the *Introduce to Metrics* part, and to understand our design of these metrics, please reference the *Class Structure* part. 

## Introduce to Metrics

### Lorenz and Kidd Metrics

#### Number of Public Methods (PM)

This simply counts **the number of public methods in a class**. 

> According to L&K, this metric is useful as an aid to estimating the amount of work to develop a class or subsystem. 

The higher PM means the higher amount of work on this class. 

#### Number of Methods (NM)

**The total number of methods in a class** counts all public, private and protected methods defined. 

> L&K suggest this metric as a useful indication of the classes which may be trying to do too much work themselves; i.e., they provide too much functionality.

The higher NM means the higher amount of work on this class. 

#### Number of Public Variables per class (NPV)

This metric counts **the number of public variables in a class**. L&K consider the number of variables in a class to be one measure of its size. 

> The fact that one class has more public variables than another might imply that the class has more relationships with other objects and, as such, is more likely to be a key class, i.e., a central point of coordination of objects within the system. 

The higher NPV means this class has more relationship with others. 

#### Number of Variables per class (NV）

This metric counts the total number of variables in a class. The total number of variables metric includes public, private and protected variables. 

> According to L&K, the ratio of private and protected variables to total number of variables indicates the effort required by that class in providing information to other classes. Private and protected variables are therefore viewed merely as data to service the methods in the class. 

NV is mainly used to calculate other metrics. 

#### Number of Methods Overridden by a subclass (NMO）

A subclass is allowed to re-define or override a method in its superclass(es) with the same name as a method in one of its superclasses. 

> According to L&K, a large number of overridden methods indicates a design problem, indicating that those methods were overridden as a design afterthought. They suggest that a subclass should really be a specialization of its super- classes, resulting in new unique names for methods. 

#### Number of Methods Added by a subclass (NMA)

> According to L&K, the normal expectation for a subclass is that it will further specialize (or add) methods to the superclass object. A method is defined as an added method in a subclass if there is no method of the same name in any of its superclasses.

#### Average Method Size (AMS)

The average method size is calculated as the number of non-comment, non- blank source lines (NCSL) in the class, divided by the number of its methods. 

> AMS is clearly a size metric, and would be useful for spotting outliers, i.e., abnormally large methods.

### Abreu Metrics

#### Polymorphism Factor (PF)

This metric is based on the number of overriding methods in a class as a ratio of the total possible number of overridden methods. 

> Polymorphism arises from inheritance, and Abreu claims that in some cases, overriding methods reduce complexity, so increasing understandability and maintainability.

#### Method Hiding Factor (MHF)

This metric is the ratio of hidden (private or protected) methods to total methods. 

> As such, MHF is proposed as a measure of encapsulation.

#### Attribute Hiding Factor (AHF)

This metric is the ratio of hidden (private or protected) attributes to total attributes. AHF is also proposed as a measure of encapsulation. 

## OOM Calculator Structure

### Classes

#### `OOMMethod`

This class is used to describe a method. The features include name of this method, `public`, `private`, `override`, `protected`. 

#### `OOMAttribute`

This class is used to describe a attribute. The features include name of attribute, `public`, `private`, `protected`. 

#### `OOMClass`

This class is used to describe a class. The feature include the name of this class, the methods containing in this class, the attribute containing in this class, the parent of this class, the children of this class. 

**Attributes**

The `metodList` contains all methods in this class, including private, public, protected, override methods. But it does not contain parent's methods. 

The `attributeList` contains all attributes in this class, including private, public, protected attributes. But it does not contain parent's attributes. 

The `ClassLength` describes the number of line of this class, used to calculate some metrics. 

The `parent` and `childrenList` create a class tree, used to calculate some metrics. 

**Methods**

Most of OOM Metrics are based on single class, so in OOM Class, there are most of metrics methods to calculate the metrics about this class and return directly. 

#### `OOMCalculator`

This is the main class of OOM Calculator, which contains the methods to calculate all the metrics of **a list of class**. 

This makes it possible for users to input a list of class and get the metrics result of those classes. And the result will be given by maps, which we believe are easy to use. 

In the mean while, there are some metrics can only be calculated based on more than one classes, so these metrics can only be calculated in this class and return a single result. 

## Summary

For users, to calculate metrics of single class, you can init `OOMClass` directly and get the result. To calculate metrics based on more than one class, you can init `OOMCalculator` by your class list and then get the result of various metrics you need. 