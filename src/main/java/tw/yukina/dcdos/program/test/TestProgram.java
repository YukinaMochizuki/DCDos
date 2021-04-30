package tw.yukina.dcdos.program.test;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.program.AbstractProgramCode;
import tw.yukina.dcdos.util.ReplyKeyboard;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestProgram extends AbstractProgramCode {

    @Override
    public String[] getKeyword() {
        return new String[]{"測試軟體1", "測試軟體1-1"};
    }

    @Override
    public String getName() {
        return "HelloWorld";
    }

    @Override
    public String getNamespace() {
        return "test";
    }

    @Override
    public String getDepiction() {
        return null;
    }

    @Override
    public void run() {
        stdout("HelloWorld");
        stdout(programController.getInput());
        stdout("ReplyMarkup", getOption("ReplyMarkup", ReplyKeyboard.oneLayerStringKeyboard(new String[]{"Key1", "Key2"})));
        stdout("HelloWorld after HelloWorld2");
    }
}
