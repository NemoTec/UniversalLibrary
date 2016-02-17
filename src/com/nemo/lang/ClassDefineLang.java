/**
 *
 *
 *
 * 1.[Class]
 *     访问修饰符 修饰符 class 类名称 extends 父类名称 implement 接口1, 接口2, 接口3...
 *     (访问修饰符与修饰符的位置可以互换)
 *     ----------------------------------------
 *     1.1 Access Modifier(访问修饰符)
 *     No01.[Class - Access Modifier: public]        |public        |public类可以被所有类访问;        |public类必须定义在同名文件中;
 *     No02.[Class - Access Modifier: none]          |无            |该类只可被同包中的类访问;        |默认的访问修饰符;
 *     ----------------------------------------
 *     1.2 Modifier(修饰符)
 *     No03.[Class - Modifier: final]                |final         |final类不可被继承                |可用在public类,也可用在包中访问类;
 *     No04.[Class - Modifier: abstract]             |abstract      |abstract类不可实例化             |可用在public类,也可用在包中访问类;类只要有一个abstract方法,类就必须定义为abstract,但abstract类不一定非要有abstract方法;
 *     ----------------------------------------
 *     1.3 Top Level Class(顶级类)
 *     
 *
 *
 *     
 *
 *
 *     
 *
 *
 *     
 *
 *
 *     ----------------------------------------
 *     1.4 Top Level Class(顶级类)
 *     
 *
 *
 *     
 *
 *
 *     
 *
 *
 *     
 *
 *
 *     
 *
 *
 *     
 *
 *
 *
 * No01.[top level class]           即顶级类,有ClassDefineLang, Plugin, Container. 
 *                                  (1)顶级类只能用public和默认修饰(可以全为默认, 不可用private, protected, static), 默认为包级可访问;
 *                                  (2)用public修饰时，类名必须与文件名相同，故只能是public class ClassDefineLang; 
 *                                  (3)用默认修饰时，类名可以全都与文件名不同(如ClassDefineLang可写成ClassDefineLangTest)。
 * No02.[]
 *内部类包含一个外部类的引用，而静态内部类不包含。
 */
package com.nemo.lang;


//No01.[Class - Access Modifier: public]
public final class ClassDefineLang {
	private int mpiNum = 1;
	//public int 
	
	
	public int getA() {
		return 1;
	}
}

//No03.[Class - Modifier: final]
final class ClassDefineLangTest {
	

}

//No04.[Class - Modifier: abstract]
abstract class Plugin {
	
	public int getB() {
		return 1;
	}
	
}

//No02.[Class - Access Modifier: none]
class Container {
	
}



class Test { 
  class A { } 
  static class B { }
  public static void main(String[] args) { 
    /*will fail - compilation error, you need an instance of Test to instantiate A*/
    //A a = new A(); 
    /*will compile successfully, not instance of Test is needed to instantiate B */
    B b = new B(); 
  }
}