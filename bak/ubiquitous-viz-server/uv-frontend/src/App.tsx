import {useEffect, useState} from "react"
import {Card, Layout} from "antd"
import {withRouter} from "react-router-dom"

import {Apps} from "./pages"
import {AppHeader, AppFooter, AppBreadcrumb} from "./components/AppAccessory"

import './App.less'
import {check} from "./services/auth"


// web menu
const menu = [
	{to: "/", name: "Home"},
	{to: "/apps", name: "Apps"}
]

// breadcrumb
const breadcrumbNameMap: Record<string, string> = {
	"/apps": "Apps",
	"/apps/datalab": "Data Lab",
}

// Web App
const App = withRouter(props => {
	const [isLogin, setIsLogin] = useState(false)
	const [userName, setUserName] = useState('')

	useEffect(() => {
		check().then(res => {
			setUserName(res.data?.nickname)
			setIsLogin(true)
		})
	}, [isLogin])

	return (
		<Layout >
			<AppHeader menu={menu} isLogin={isLogin} />
			<Layout.Content>
				<AppBreadcrumb
					{...props}
					breadcrumbNameMap={breadcrumbNameMap}
				/>
				<Card>
					<Apps
						userName={userName}
						isLogin={isLogin}
						setIsLogin={setIsLogin}
					/>
				</Card>
			</Layout.Content>
			<AppFooter />
		</Layout>
	)
})

export default App
