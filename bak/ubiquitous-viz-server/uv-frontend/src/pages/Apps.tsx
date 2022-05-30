import {Link, Switch, Route} from "react-router-dom"

import {DataLab} from "./DataLab"

import {Home, Invitation, LoginPage, LogoutPage, RegisterPage} from "./"

interface AppsProps {
    userName: string
    isLogin: boolean
    setIsLogin: React.Dispatch<React.SetStateAction<boolean>>
}

// Router redirect
export const Apps = (props: AppsProps) => {
    return (
        <Switch>
            <Route path="/" exact  >
                <Home userName={props.userName}
                    isLogin={props.isLogin} />
            </Route>
            <Route path="/apps" >
                {
                    props.isLogin ?
                        < AppList /> :
                        <>
                            Welcome visitor! Please <a href="#/login" style={{textDecoration: "underline"}}> Login</a> first!
                        </>
                }
            </Route>
            <Route path="/login">
                <LoginPage setLoginState={props.setIsLogin} />
            </Route>
            <Route path="/logout" >
                <LogoutPage setLoginState={props.setIsLogin} />
            </Route>
            <Route path="/registration" component={RegisterPage} />
            <Route path="/register" component={Invitation} />
        </Switch>
    )
}

const AppList = () => {
    return (
        <>
            <ul className="app-list">
                <li>
                    <Link to="/apps/datalab">DataLab</Link>
                </li>
            </ul>
            <Switch>
                <Route path="/apps/datalab" component={DataLab} />
            </Switch>
        </>
    )
}