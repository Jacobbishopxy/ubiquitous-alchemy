import {Link, RouteComponentProps} from "react-router-dom"
import {Breadcrumb} from "antd"


interface AppProps extends RouteComponentProps<any> {
    breadcrumbNameMap: Record<string, string>
}

export const AppBreadcrumb = (props: AppProps) => {
    const pathSnippets = props.location.pathname.split('/').filter(i => i)
    const extraBreadcrumbItems = pathSnippets.map((_, index) => {
        const url = `/${pathSnippets.slice(0, index + 1).join('/')}`
        return (
            <Breadcrumb.Item key={url}>
                <Link to={url}>{props.breadcrumbNameMap[url]}</Link>
            </Breadcrumb.Item>
        )
    })


    return (
        <Breadcrumb>
            {
                [
                    <Breadcrumb.Item key="home">
                        <Link to="/">Home</Link>
                    </Breadcrumb.Item>,
                ].concat(extraBreadcrumbItems)
            }
        </Breadcrumb>
    )
}
