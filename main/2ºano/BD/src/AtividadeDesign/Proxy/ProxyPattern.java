package AtividadeDesign.Proxy;

public class ProxyPattern {
    public static void main(String[] args) {
        System.out.println(
        new Cliente().greet("Renato"));
    }
    
}

interface SubjectInterface{
    String greet(String name);
}

class  RealSubject implements SubjectInterface{

    @Override
    public String greet(String name){
        return "Hello " + name;
    }
}

class RealSubjectProxy implements SubjectInterface{
    private RealSubject realSubject = new RealSubject();

    @Override
    public String greet(String name){
        System.out.println("Proxy: loogging before greeting");
        String result = realSubject.greet(name);
        System.out.println("Proxy: loogging after greeting");
        return result;
    }
}

class Cliente{
    public String greet (String name){
        var realSubjectProxy = new RealSubjectProxy();

        return realSubjectProxy.greet(name);
    }
}