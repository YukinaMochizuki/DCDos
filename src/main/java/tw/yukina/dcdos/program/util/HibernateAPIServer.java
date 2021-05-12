package tw.yukina.dcdos.program.util;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.manager.APIManager;
import tw.yukina.dcdos.program.AbstractProgramCode;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HibernateAPIServer extends AbstractProgramCode {

    private final APIManager apiManager;

    public HibernateAPIServer(APIManager apiManager) {
        this.apiManager = apiManager;
    }

    @Override
    public String[] getKeyword() {
        return new String[]{"休眠"};
    }

    @Override
    public String getName() {
        return "Hibernate";
    }

    @Override
    public String getNamespace() {
        return "util";
    }

    @Override
    public String getDepiction() {
        return null;
    }

    @Override
    public void run() {
        apiManager.hibernate();
    }
}
