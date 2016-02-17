/**
 *
 *
 *
 * 1.[Class]
 *     �������η� ���η� class ������ extends �������� implement �ӿ�1, �ӿ�2, �ӿ�3...
 *     (�������η������η���λ�ÿ��Ի���)
 *     ----------------------------------------
 *     1.1 Access Modifier(�������η�)
 *     No01.[Class - Access Modifier: public]        |public        |public����Ա����������;        |public����붨����ͬ���ļ���;
 *     No02.[Class - Access Modifier: none]          |��            |����ֻ�ɱ�ͬ���е������;        |Ĭ�ϵķ������η�;
 *     ----------------------------------------
 *     1.2 Modifier(���η�)
 *     No03.[Class - Modifier: final]                |final         |final�಻�ɱ��̳�                |������public��,Ҳ�����ڰ��з�����;
 *     No04.[Class - Modifier: abstract]             |abstract      |abstract�಻��ʵ����             |������public��,Ҳ�����ڰ��з�����;��ֻҪ��һ��abstract����,��ͱ��붨��Ϊabstract,��abstract�಻һ����Ҫ��abstract����;
 *     ----------------------------------------
 *     1.3 Top Level Class(������)
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
 *     1.4 Top Level Class(������)
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
 * No01.[top level class]           ��������,��ClassDefineLang, Plugin, Container. 
 *                                  (1)������ֻ����public��Ĭ������(����ȫΪĬ��, ������private, protected, static), Ĭ��Ϊ�����ɷ���;
 *                                  (2)��public����ʱ�������������ļ�����ͬ����ֻ����public class ClassDefineLang; 
 *                                  (3)��Ĭ������ʱ����������ȫ�����ļ�����ͬ(��ClassDefineLang��д��ClassDefineLangTest)��
 * No02.[]
 *�ڲ������һ���ⲿ������ã�����̬�ڲ��಻������
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