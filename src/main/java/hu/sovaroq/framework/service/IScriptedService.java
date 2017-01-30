package hu.sovaroq.framework.service;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IScriptedService extends IService {
    void initialize(String script);
    void restartScript();
    void restartScript(String newScript);

    String getScriptFile();
}
