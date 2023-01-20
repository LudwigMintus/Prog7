package command;

public enum CommandsEnum {
    ADD("add"), //+ +
    ADD_IF_MIN("add_if_min"), // + +
    CLEAR("clear"), //+ +
    EXECUTE_SCRIPT("execute_script"), // + +
    EXIT("exit"), // + +
    FILTER_STARTS_WITH_NAME("filter_starts_with_name"), // + +
    HELP("help"), // + +
    INFO("info"), // + +
    REMOVE_BY_ID("remove_by_id"), //+ +
    REMOVE_LOWER("remove_lower"), // + +
    SHOW("show"),//+ +
    UPDATE("update"), // ++
    AUTHORIZATION("authorization"), // +
    ADD_IF_MAX("add_if_max"), // + +
    COUNT_BY_IMPACT_SPEED("count_by_impact_speed"),// реализовать

    PRINT_FIELD_DESCENDING_WEAPON_TYPE("print_field_descending_weapon_type");// реализовать

    public String title;

    CommandsEnum(String name) {
        this.title = name;
    }
}
