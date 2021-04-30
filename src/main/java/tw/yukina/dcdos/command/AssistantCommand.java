package tw.yukina.dcdos.command;


import tw.yukina.dcdos.constants.Role;

public interface AssistantCommand {
    public String getCommandName();
    public Role[] getPermissions();
}
