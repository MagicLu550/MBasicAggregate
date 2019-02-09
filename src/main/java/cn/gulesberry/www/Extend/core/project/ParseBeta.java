package cn.gulesberry.www.Extend.core.project;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用Beta注解后,可以通过ControlBeta方法控制该方法流程
 */
public final class ParseBeta implements MethodParser {
    private static ParseBeta beta = new ParseBeta();
    Map<String,String> map = new HashMap<>();
    public Map<String,String> parse(Object o, String method, Class<?>... args)
            throws NoSuchMethodException, AnnotationNotFoundException {
        //获取cls
        Class cls = o.getClass();
        //获取方法
        Method m = cls.getDeclaredMethod(method,args);
        Annotation[] annos = m.getAnnotations();
        containsAll(annos);
        return map;
    }
    public Map<String, String> parse(Object o, String method)
            throws NoSuchMethodException, AnnotationNotFoundException {
        //获取cls
        Class cls = o.getClass();
        //获取方法
        Method m = cls.getDeclaredMethod(method);
        Annotation[] annos = m.getAnnotations();
        containsAll(annos);
        return map;
    }
    public void containsAll(Annotation[] annos) throws AnnotationNotFoundException {
        Annotation annotation = null;
        boolean isHave = false;
        for(Annotation a:annos){
            if(a instanceof Beta){
                annotation = a;
                isHave = true;
            }
        }
        if(isHave == false){
            throw new AnnotationNotFoundException("This Method do not have BetaAnnotation");
        }
        String anno = annotation.toString();
        String arg = anno.substring(anno.indexOf("(")+1,anno.indexOf(")"));
        String[] allVar = arg.split(",");
        for(int i = 0;i<allVar.length;i++){
            String[] keyOrValue = allVar[0].split("=");
            map.put(keyOrValue[0],keyOrValue[1]);
        }
    }
    public String getProgress(){
        return map.get("progress");
    }
    public void Control(String progress){
        if(compl("START")){
            System.out.println("[WARNNING] 这个方法刚开始工程,请注意其实用性");
        }else if(compl("DEVELOPING")){
            System.out.println("[WARNNING] 这个方法正在开发中,不会被执行");
            Thread.interrupted();
        }else if(compl("BUG")){
            System.out.println("[WARNNING] 这个方法有BUG问题.请等待修复");
            Thread.interrupted();
        }else if(compl("FINISH")){
        }else if(compl("UPDATING")){
            System.out.println("[INFO] 正在更新,请注意查看官网");
        }
    }
    private boolean compl(String Progress){
        return getProgress().equals(Progress);
    }
    public static void ControlBeta(Object o,String method,Class<?>... args)
            throws NoSuchMethodException, AnnotationNotFoundException {
        beta.parse(o,method,args);
        beta.Control(beta.getProgress());
    }
    public static void ControlBeta(Object o,String method)
            throws NoSuchMethodException, AnnotationNotFoundException {
        beta.parse(o,method);
        beta.Control(beta.getProgress());
    }
}
