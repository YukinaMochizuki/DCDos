package tw.yukina.dcdos.manager;

import org.springframework.stereotype.Service;
import tw.yukina.dcdos.program.AbstractProgramCode;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class ProgramManager {

    private final Collection<AbstractProgramCode> programCodes;

    public ProgramManager(Collection<AbstractProgramCode> programCodes) {
        this.programCodes = programCodes;
    }

    public Class<? extends AbstractProgramCode> findProgram(String fullName){

        Optional<AbstractProgramCode> findAbstractProgramCode =
                programCodes.stream().filter(abstractProgramCode -> fullName.equals(abstractProgramCode.getFullName())).findAny();

        return findAbstractProgramCode.<Class<? extends AbstractProgramCode>>map(AbstractProgramCode::getClass).orElse(null);
    }

    public Class<? extends AbstractProgramCode> findProgramByKeyWord(String keyword){

        Optional<AbstractProgramCode> findAbstractProgramCode = programCodes.stream().
                filter(abstractProgramCode -> Arrays.asList(abstractProgramCode.getKeyword()).contains(keyword)).findAny();

        return findAbstractProgramCode.<Class<? extends AbstractProgramCode>>map(AbstractProgramCode::getClass).orElse(null);
    }
}
