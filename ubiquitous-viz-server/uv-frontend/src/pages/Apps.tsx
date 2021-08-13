import {Link} from 'react-router-dom'


export const Apps = () => {

    return (
        <ul className="app-list">
            <li>
                <Link to="/apps/datalab">DataLab</Link>
            </li>
        </ul>
    )
}
