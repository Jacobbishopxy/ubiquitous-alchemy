import {Link, Switch, Route} from 'react-router-dom'
import {DataLab} from "./DataLab"

export const breadcrumbNameMap: Record<string, string> = {
    '/apps': 'Apps',
    '/apps/datalab': 'Data Lab',
}

export const Apps = () => {

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
