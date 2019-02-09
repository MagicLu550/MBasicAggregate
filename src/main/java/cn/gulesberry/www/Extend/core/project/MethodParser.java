package cn.gulesberry.www.Extend.core.project;

import java.util.Map;

public interface MethodParser {
    Map<String,String> parse(Object o, String method, Class<?>... cls) throws Exception;
    Map<String,String> parse(Object o, String Method) throws Exception;
}
