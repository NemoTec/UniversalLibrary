/**
 *
 *
 * =================================================================================================
 * 1.[Class]
 *     ~{7CNJP^JN7{~} ~{P^JN7{~} class ~{@`C{3F~} extends ~{88@`C{3F~} implement ~{=S?Z~}1, ~{=S?Z~}2, ~{=S?Z~}3...
 *     (~{7CNJP^JN7{SkP^JN7{5DN;VC?IRT;%;;~})
 *
 *     ----------------------------------------
 *     1.1 Access Modifier(~{7CNJP^JN7{~})
 *     No01.[Class - Access Modifier: public]        |public        |public~{@`?IRT1;KySP@`7CNJ~};        |public~{@`1XPk6(ReTZM,C{ND<~VP~};~{R;8v~}java~{ND<~?IRTC;SP~}public~{@`~};
 *     No02.[Class - Access Modifier: none]          |~{N^~}            |~{8C@`V;?I1;M,0|VP5D@`7CNJ~};        |~{D,HO5D7CNJP^JN7{~};~{R;8v~}java~{ND<~?IRTC;SPM,C{5D@`#,5+K|CG1XPkH+JG0|<6?I7CNJH(O^!#~}
 *
 *     ----------------------------------------
 *     1.2 Modifier(~{P^JN7{~})
 *     No03.[Class - Modifier: final]                |final         |final~{@`2;?I1;<L3P~}                |~{?ISCTZ~}public~{@`~},~{R2?ISCTZ0|VP7CNJ@`~};
 *     No04.[Class - Modifier: abstract]             |abstract      |abstract~{@`2;?IJ5@};/~}             |~{?ISCTZ~}public~{@`~},~{R2?ISCTZ0|VP7CNJ@`~};~{@`V;R*SPR;8v~}abstract~{7=7(~},~{@`>M1XPk6(ReN*~}abstract,~{5+~}abstract~{@`2;R;6(7GR*SP~}abstract~{7=7(~};
 *
 *     ----------------------------------------
 *     1.3 Top Level Class(~{6%<6@`~})
 *     ~{6%<6@`~},~{SP~}ClassDefineLang, Plugin, Container. 
 *     (1)~{6%<6@`V;D\SC~}public~{:MD,HOP^JN~}(~{?IRTH+N*D,HO~}, ~{2;?ISC~}private, protected, static), ~{D,HON*0|<6?I7CNJ~};
 *     (2)~{SC~}public~{P^JNJ1#,@`C{1XPkSkND<~C{O`M,#,9JV;D\JG~}public class ClassDefineLang; 
 *     (3)~{SCD,HOP^JNJ1#,@`C{?IRTH+6<SkND<~C{2;M,~}(~{Hg~}ClassDefineLang~{?IP43I~}ClassDefineLangTest)~{!#~}     
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
 *     1.4 Inner Class(~{DZ2?@`~})
 *     ~{DZ2?@`0|:,R;8vMb2?@`5DR}SC#,6x>2L,DZ2?@`2;0|:,!#~}
 *
 *
 *     
 *     ----------------------------------------
 *     1.5 Field(~{3IT1~})
 *
 *
 *
 *
 *
 *
 *     ----------------------------------------
 *     1.6 Method(~{7=7(~})
 *
 *
 *
 *
 *
 *
 *
 *
 *     
 * =================================================================================================
 * 2.[interface]
 *     xxxxx
 *     ----------------------------------------
 *     1.5 Inner Class(~{DZ2?@`~})
 *
 *
 *     
 *
 *
 *     
 *
 *
 *
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