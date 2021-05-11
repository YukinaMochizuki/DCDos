package tw.yukina.dcdos.program;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractProgramCode {

    @Getter
    @Setter
    private boolean isDisable = false;

    @Getter
    @Setter
    private boolean displayHeader = true;

    @Autowired
    protected ApplicationContext applicationContext;

    protected ProgramController programController;

    public String getFullName(){
        return getNamespace() + "." + getName();
    }

    public void setProgramController(ProgramController programController){
        if(this.programController == null)this.programController = programController;
    }

    protected void call(String fullName){
        programController.call(fullName);
    }

    protected void callByKeyWord(String keyWord){
        programController.callByKeyWord(keyWord);
    }

    protected void fork(String fullName){
        programController.fork(fullName);
    }

    protected void forkByKeyWord(String keyWord){
        programController.forkByKeyWord(keyWord);
    }

    protected void stdout(Map<String, Object> messageWithOption){
        programController.putStdout(messageWithOption);
    }

    protected void stdout(String message, Map<String, Object> option){
        option.put("Message", message);
        programController.putStdout(option);
    }

    protected void stdout(String message){
        programController.putStdoutMessage(message);
    }

    protected void stdout(String message, String uuid){
        programController.putStdoutMessage(message, uuid);
    }

    protected void stderr(String message){
        programController.putStderr(message);
    }

    protected void updateStdout(Map<String, Object> messageMap){
        programController.putUpdateStdout(messageMap);
    }

    protected void updateStdout(String message, String uuid){
        programController.putUpdateStdout(message, uuid);
    }

    protected String getInput(){
        return programController.getInput();
    }

    protected Map<String, Object> getOption(String key, Object value){
        Map<String, Object> option = new HashMap<>();
        option.put(key, value);
        return option;
    }

    public abstract String[] getKeyword();

    public abstract String getName();

    public abstract String getNamespace();

    public abstract String getDepiction();

    public abstract void run();
}
