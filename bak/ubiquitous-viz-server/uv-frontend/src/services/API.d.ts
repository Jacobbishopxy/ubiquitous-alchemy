declare namespace API {
    interface ConnInfo {
        id?: string
        name?: string
        description?: string
        driver: string
        username: string
        password: string
        host: string
        port: number
        database: string
    }

    // interface for select query
    interface Selection {
        table: string,
        columns: ColumnAlias[],
        filter?: Expression[],
        order?: Order[],
        limit?: number,
        offset?: number,

    }

    type DataEnum = number | string | boolean | null

    type ColumnAlias = Simple | Alias
    type Simple = string
    type Alias = [string, string]

    type Expression = Conjunction | Condition | Expression[]
    enum Conjunction {
        AND = "AND",
        OR = "OR",
    }
    interface Condition {
        column: string,
        equation: Equation,
    }
    //TODO: could only contain one of the following
    interface Equation {
        Equal?: DataEnum,
        NotEqual?: DataEnum,
        Greater?: DataEnum,
        GreaterEqual?: DataEnum,
        Less?: DataEnum,
        LessEqual?: DataEnum,
        In?: DataEnum[],
        Between?: [DataEnum, DataEnum],
        Like?: string,
    }
    enum ConditionToString {
        Equal = "Equal",
        NotEqual = "NotEqual",
        Greater = "Greater",
        GreaterEqual = "GreaterEqual",
        Less = "Less",
        LessEqual = "LessEqual",
        In = "In",
        Between = "Between",
        Like = "Like",
    }

    interface Order {
        name: String,
        order?: OrderType,
    }
    enum OrderType {
        Asc = "Asc",
        Desc = "Desc"
    }

    interface Invitation {
        nickname: string,
        email: string,
        password: string,
    }

    interface Registration {
        email: string,
    }

    interface Login {
        email: string,
        password: string,
    }

    interface LoginCheck {
        data: LoginCheckData
    }

    interface LoginCheckData {
        email: string,
        role: string,
        nickname: string,
    }
}
