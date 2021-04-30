package tw.yukina.dcdos.program.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.manager.ProgramManager;
import tw.yukina.dcdos.program.AbstractProgramCode;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Shell extends AbstractProgramCode {

    @Lazy
    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private ProgramManager programManager;

    @Override
    public String[] getKeyword() {
        return new String[]{"shell"};
    }

    @Override
    public String getName() {
        return "shell";
    }

    @Override
    public String getNamespace() {
        return "core";
    }

    @Override
    public String getDepiction() {
        return null;
    }

    @Override
    public void run() {
        stdout("shell $");
        while (true){
            String input = getInput();

            if(input.contains("$exit")){
                stdout("shell exit");
                break;
            }else {
                if(programManager.findProgramByKeyWord(input) == null){
                    stdout("Keyword not found");
                    continue;
                }
                callByKeyWord(input);
            }
        }
    }
}