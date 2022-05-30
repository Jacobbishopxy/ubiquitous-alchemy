
export interface homeProps {
    isLogin: boolean
    userName: string
}
export const Home = (props: homeProps) => {
    return (props.isLogin ?
        <>
            Hi {props.userName} & Welcome
        </> :
        <>
            Welcome visitor! Please<a href="#/login" style={{textDecoration: "underline"}}> Login</a> first!
        </>
    )
}
