package tw.yukina.dcdos.program.test;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.program.AbstractProgramCode;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestEditMessage extends AbstractProgramCode {

    @Override
    public String[] getKeyword() {
        return new String[]{"edit test"};
    }

    @Override
    public String getName() {
        return "testEdit";
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
        stdout("testMessage", "uuid1");
        stdout("testMessage2", "uuid2");

        try {
            Thread.sleep(1000);
            stdout("after");
            updateStdout("after edit", "uuid1");
            updateStdout("after edit2", "uuid2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
