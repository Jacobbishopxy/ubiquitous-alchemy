/*
DataLab
*/

import {Tabs} from "antd"

import {checkConnection, createConn, listConn, deleteConn, updateConn} from "../services"
import {DatabaseConfiguration, SelectionModalForm} from "../components"

//To Delete
import {tableNameEnum, columnNameEnum} from "../components/QuerySelector/temp"


export const DataLab = () => {
    return (
        <div style={{width: "100%", backgroundColor: "white"}}>
            <Tabs style={{margin: "10px"}}>
                <Tabs.TabPane tab="Database Configuration" key="db_config">
                    <DatabaseConfiguration
                        checkConnection={checkConnection}
                        listConn={listConn}
                        createConn={createConn}
                        updateConn={updateConn}
                        deleteConn={deleteConn}
                    />
                </Tabs.TabPane>
                <Tabs.TabPane tab="Selection" key="select">
                    <SelectionModalForm key='select' tableNameEnum={tableNameEnum} columnNameEnum={columnNameEnum} />
                </Tabs.TabPane>
            </Tabs>
        </div>
    )
}

