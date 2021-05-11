package tw.yukina.dcdos.program;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tw.yukina.dcdos.constants.ProgramStatus;
import tw.yukina.dcdos.manager.ProgramManager;
import tw.yukina.dcdos.session.AbstractSession;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ProgramController {

    private final Register register;

    private final ProgramManager programManager;

    private final AbstractSession abstractSession;

    private final String id;

    @Getter
    @Setter
    private ProgramStatus programStatus = ProgramStatus.READY;

    public void fork(String fullName){
        abstractSession.fork(programManager.findProgram(fullName));
    }

    public void forkByKeyWord(String keyWord){
        abstractSession.fork(programManager.findProgramByKeyWord(keyWord));
    }

    public void call(String fullName){
        abstractSession.call(programManager.findProgram(fullName));
    }

    public void callByKeyWord(String keyWord){
        abstractSession.call(programManager.findProgramByKeyWord(keyWord));
    }

    public String getInput(){
        synchronized(this) {
            if (register.getStdin().peek() == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return register.getStdin().poll();
    }

    public void putUserInput(String input){
        register.getStdin().offer(input);
        synchronized(this) {
            notify();
        }
    }

    public void putStdout(Map<String, Object> messageMap){
        register.getStdout().offer(messageMap);
        if(programStatus.equals(ProgramStatus.RUNNING))abstractSession.refresh();
    }

    public void putStdoutMessage(String message){
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("Message", message);
        putStdout(messageMap);
    }

    public void putStdoutMessage(String message, String uuid){

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("Message", message);
        messageMap.put("UUID", uuid);

        putStdout(messageMap);
    }

    public void putUpdateStdout(Map<String, Object> messageMap){
        register.getUpdateStdout().add(messageMap);
        if(programStatus.equals(ProgramStatus.RUNNING)){
            abstractSession.refresh();
        }
    }

    public void putUpdateStdout(String message, String uuid){

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("Message", message);
        messageMap.put("UUID", uuid);

        putUpdateStdout(messageMap);
    }

    public void putStderr(String message){
        register.getStderr().offer(message);
        if(programStatus.equals(ProgramStatus.RUNNING)){
            abstractSession.refresh();
        }
    }
}
