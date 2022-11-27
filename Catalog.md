    .
    ├── bak
    │   ├── golang-api
    │   │   ├── Dockerfile
    │   │   ├── docker-compose.yml
    │   │   ├── setup.sh
    │   │   └── start.sh
    │   ├── ubiquitous-auth-server
    │   │   ├── src
    │   │   │   ├── bin
    │   │   │   ├── handler
    │   │   │   │   ├── auth.rs
    │   │   │   │   ├── invitation.rs
    │   │   │   │   ├── mod.rs
    │   │   │   │   └── register.rs
    │   │   │   ├── model
    │   │   │   │   ├── invitation.rs
    │   │   │   │   ├── mod.rs
    │   │   │   │   └── user.rs
    │   │   │   ├── service
    │   │   │   │   ├── email.rs
    │   │   │   │   ├── encryption.rs
    │   │   │   │   ├── mod.rs
    │   │   │   │   └── persistence.rs
    │   │   │   ├── util
    │   │   │   │   ├── email_helper.rs
    │   │   │   │   ├── encryption_helper.rs
    │   │   │   │   └── mod.rs
    │   │   │   ├── constant.rs
    │   │   │   ├── error.rs
    │   │   │   ├── lib.rs
    │   │   │   └── main.rs
    │   │   └── README.md
    │   ├── ubiquitous-data-server
    │   │   ├── ua-application
    │   │   │   └── src
    │   │   │       ├── controller
    │   │   │       │   ├── configuration.rs
    │   │   │       │   ├── mod.rs
    │   │   │       │   ├── query.rs
    │   │   │       │   └── schema.rs
    │   │   │       ├── model
    │   │   │       │   ├── biz_model.rs
    │   │   │       │   ├── mod.rs
    │   │   │       │   └── persistence.rs
    │   │   │       ├── service
    │   │   │       │   ├── mod.rs
    │   │   │       │   ├── query.rs
    │   │   │       │   └── schema.rs
    │   │   │       ├── constant.rs
    │   │   │       ├── error.rs
    │   │   │       ├── lib.rs
    │   │   │       └── main.rs
    │   │   ├── ua-persistence
    │   │   │   └── src
    │   │   │       ├── lib.rs
    │   │   │       └── model.rs
    │   │   ├── ua-service
    │   │   │   └── src
    │   │   │       ├── dao
    │   │   │       │   ├── mod.rs
    │   │   │       │   └── model.rs
    │   │   │       ├── interface
    │   │   │       │   ├── mod.rs
    │   │   │       │   ├── query.rs
    │   │   │       │   └── schema.rs
    │   │   │       ├── provider
    │   │   │       │   ├── mod.rs
    │   │   │       │   └── sea.rs
    │   │   │       ├── repository
    │   │   │       │   ├── mod.rs
    │   │   │       │   ├── query.rs
    │   │   │       │   └── schema.rs
    │   │   │       ├── util
    │   │   │       │   ├── general.rs
    │   │   │       │   ├── mod.rs
    │   │   │       │   └── type_conversion.rs
    │   │   │       ├── errors.rs
    │   │   │       └── lib.rs
    │   │   ├── Makefile
    │   │   └── README.md
    │   ├── ubiquitous-viz-server
    │   │   ├── uv-backend
    │   │   │   └── src
    │   │   │       ├── constant.rs
    │   │   │       ├── frontend.rs
    │   │   │       ├── lib.rs
    │   │   │       ├── main.rs
    │   │   │       ├── proxy_agent.rs
    │   │   │       └── util.rs
    │   │   ├── uv-frontend
    │   │   │   ├── public
    │   │   │   │   ├── favicon.ico
    │   │   │   │   ├── index.html
    │   │   │   │   ├── logo192.png
    │   │   │   │   ├── logo512.png
    │   │   │   │   └── robots.txt
    │   │   │   ├── src
    │   │   │   │   ├── components
    │   │   │   │   │   ├── AppAccessory
    │   │   │   │   │   │   ├── AppBreadcrumb.tsx
    │   │   │   │   │   │   ├── AppFooter.tsx
    │   │   │   │   │   │   ├── AppHeader.tsx
    │   │   │   │   │   │   └── index.ts
    │   │   │   │   │   ├── DatabaseConfiguration
    │   │   │   │   │   │   ├── DatabaseConfiguration.tsx
    │   │   │   │   │   │   ├── DatabaseModalForm.tsx
    │   │   │   │   │   │   └── index.ts
    │   │   │   │   │   ├── Login
    │   │   │   │   │   │   ├── Login.less
    │   │   │   │   │   │   ├── Login.tsx
    │   │   │   │   │   │   ├── Registration.tsx
    │   │   │   │   │   │   └── index.ts
    │   │   │   │   │   ├── QuerySelector
    │   │   │   │   │   │   ├── SelectionConditionItem.tsx
    │   │   │   │   │   │   ├── SelectionFilter.tsx
    │   │   │   │   │   │   ├── SelectionModalForm.tsx
    │   │   │   │   │   │   ├── SelectionOrder.tsx
    │   │   │   │   │   │   ├── index.ts
    │   │   │   │   │   │   └── temp.tsx
    │   │   │   │   │   └── index.ts
    │   │   │   │   ├── pages
    │   │   │   │   │   ├── Apps.tsx
    │   │   │   │   │   ├── DataLab.tsx
    │   │   │   │   │   ├── Home.tsx
    │   │   │   │   │   ├── Invitation.tsx
    │   │   │   │   │   ├── Login.tsx
    │   │   │   │   │   ├── Logout.tsx
    │   │   │   │   │   ├── Register.tsx
    │   │   │   │   │   └── index.ts
    │   │   │   │   ├── services
    │   │   │   │   │   ├── API.d.ts
    │   │   │   │   │   ├── auth.ts
    │   │   │   │   │   ├── database_configuration.ts
    │   │   │   │   │   ├── index.ts
    │   │   │   │   │   └── select_configuration.ts
    │   │   │   │   ├── App.less
    │   │   │   │   ├── App.test.tsx
    │   │   │   │   ├── App.tsx
    │   │   │   │   ├── index.css
    │   │   │   │   ├── index.tsx
    │   │   │   │   ├── logo.svg
    │   │   │   │   ├── react-app-env.d.ts
    │   │   │   │   ├── reportWebVitals.ts
    │   │   │   │   └── setupTests.ts
    │   │   │   ├── README.md
    │   │   │   └── craco.config.js
    │   │   ├── Makefile
    │   │   └── README.md
    │   ├── Makefile.bk
    │   └── README.md
    ├── dev
    │   ├── Makefile
    │   ├── README.md
    │   └── dev.env
    ├── docker-app
    │   ├── asset-management
    │   │   ├── Dockerfile
    │   │   ├── docker-compose.yml
    │   │   ├── setup.sh
    │   │   └── start.sh
    │   ├── auth
    │   │   ├── Dockerfile
    │   │   ├── docker-compose.yml
    │   │   ├── setup.sh
    │   │   └── start.sh
    │   ├── biz-api
    │   │   ├── Dockerfile
    │   │   ├── docker-compose.yml
    │   │   ├── restart.sh
    │   │   ├── setup.sh
    │   │   └── start.sh
    │   ├── biz-auth
    │   │   ├── Dockerfile
    │   │   ├── docker-compose.yaml
    │   │   ├── setup.sh
    │   │   └── start.sh
    │   ├── biz-base
    │   │   ├── Dockerfile
    │   │   └── setup.sh
    │   ├── biz-db
    │   │   └── create_database.sh
    │   ├── biz-gateway
    │   │   ├── Dockerfile
    │   │   ├── docker-compose.yaml
    │   │   ├── setup.sh
    │   │   └── start.sh
    │   ├── py-api
    │   │   ├── Dockerfile
    │   │   ├── docker-compose.yml
    │   │   ├── setup.sh
    │   │   └── start.sh
    │   ├── py-base
    │   │   ├── Dockerfile
    │   │   └── setup.sh
    │   ├── resource-centre
    │   │   ├── Dockerfile
    │   │   ├── docker-compose.yml
    │   │   ├── setup.sh
    │   │   └── start.sh
    │   ├── web-app
    │   │   ├── Dockerfile.backend
    │   │   ├── Dockerfile.frontend
    │   │   ├── README.md
    │   │   ├── build-backend.sh
    │   │   ├── build-frontend.sh
    │   │   ├── docker-compose.yml
    │   │   └── start.sh
    │   ├── web-base
    │   │   ├── Dockerfile
    │   │   ├── README.md
    │   │   └── setup.sh
    │   └── README.md
    ├── docker-base
    │   ├── go
    │   │   ├── Dockerfile
    │   │   └── setup.sh
    │   ├── java
    │   │   ├── Dockerfile
    │   │   ├── pull-maven.sh
    │   │   └── setup.sh
    │   ├── mongodb
    │   │   ├── README.md
    │   │   ├── create_unique_index.sh
    │   │   ├── docker-compose.yml
    │   │   └── start.sh
    │   ├── nodejs
    │   │   └── setup.sh
    │   ├── postgres
    │   │   ├── README.md
    │   │   ├── create_database.sh
    │   │   ├── docker-compose.yml
    │   │   ├── grant_read_all.sh
    │   │   └── start.sh
    │   ├── py
    │   │   └── setup.sh
    │   ├── rust
    │   │   ├── Dockerfile
    │   │   ├── setup.sh
    │   │   └── sources.list
    │   ├── README.md
    │   └── docker-create-network.sh
    ├── inn
    │   └── inn.sqlite
    ├── notes
    │   ├── draw
    │   │   ├── Fabrix.drawio
    │   │   ├── Microservice.drawio
    │   │   ├── WebServer.drawio
    │   │   ├── framework.drawio
    │   │   └── project_structure.drawio
    │   ├── Documentatiaon-En.md
    │   ├── Documentation.CN.md
    │   ├── Documentation.EN.md
    │   ├── Fabrix.svg
    │   ├── Microservice.svg
    │   ├── ProjectDescription.CN.md
    │   ├── WebServer.svg
    │   ├── portfolio.drawio
    │   ├── project_structure.drawio
    │   ├── structure.png
    │   └── sturcture.png
    ├── public
    │   ├── document
    │   │   ├── GalleryDataStructure.svg
    │   │   └── NewRocket.png
    │   ├── icons
    │   │   ├── icon-128x128.png
    │   │   ├── icon-192x192.png
    │   │   └── icon-512x512.png
    │   ├── manual
    │   │   ├── category-config.jpg
    │   │   ├── dashboard-config.jpg
    │   │   ├── dashboard-edit1.jpg
    │   │   ├── dashboard-edit2.jpg
    │   │   ├── dashboard-edit3.jpg
    │   │   ├── dashboard-empty-tab-pane.png
    │   │   ├── dashboard-new.jpg
    │   │   ├── dashboard-new1.jpg
    │   │   ├── dashboard-new2.jpg
    │   │   ├── dashboard-new3.jpg
    │   │   ├── dashboard-new4.jpg
    │   │   ├── dashboard.jpg
    │   │   ├── database-config.jpg
    │   │   ├── database-config1.jpg
    │   │   ├── dataset-file-format.jpg
    │   │   ├── dataset-query.jpg
    │   │   ├── dataset-query1.jpg
    │   │   ├── dataset-upload.jpg
    │   │   ├── dataset.jpg
    │   │   ├── module-file-management.jpg
    │   │   ├── module-file-management1.jpg
    │   │   ├── module-line-bar.jpg
    │   │   ├── module-line-bar1.jpg
    │   │   ├── module-line.jpg
    │   │   ├── module-line1.jpg
    │   │   ├── module-line2.jpg
    │   │   ├── module-line3.jpg
    │   │   ├── module-link.jpg
    │   │   ├── module-link1.jpg
    │   │   ├── module-scatter.jpg
    │   │   ├── module-scatter1.jpg
    │   │   ├── module-table-flex.jpg
    │   │   ├── module-table-flex1.jpg
    │   │   ├── module-table-flex2.jpg
    │   │   ├── module-table-flex3.jpg
    │   │   ├── module-table-flex4.jpg
    │   │   ├── module-table-xlsx.jpg
    │   │   ├── module-table-xlsx1.jpg
    │   │   ├── module-table-xlsx2.jpg
    │   │   ├── module-table-xlsx3.jpg
    │   │   ├── module-target-price.jpg
    │   │   ├── module-target-price1.jpg
    │   │   ├── module-text.jpg
    │   │   └── module-text1.jpg
    │   ├── unicorn
    │   │   ├── 001-unicorn.svg
    │   │   ├── 002-unicorn.svg
    │   │   ├── 003-eye mask.svg
    │   │   ├── 004-unicorn.svg
    │   │   ├── 005-unicorn.svg
    │   │   ├── 006-unicorn.svg
    │   │   ├── 007-unicorn.svg
    │   │   ├── 008-unicorn.svg
    │   │   ├── 009-cake.svg
    │   │   ├── 010-unicorn.svg
    │   │   ├── 011-unicorn.svg
    │   │   ├── 012-unicorn.svg
    │   │   ├── 013-unicorn.svg
    │   │   ├── 014-unicorn.svg
    │   │   ├── 015-unicorn.svg
    │   │   ├── 016-unicorn.svg
    │   │   ├── 017-unicorn.svg
    │   │   ├── 018-unicorn.svg
    │   │   ├── 019-unicorn.svg
    │   │   ├── 020-unicorn.svg
    │   │   ├── 021-cupcake.svg
    │   │   ├── 022-unicorn.svg
    │   │   ├── 023-unicorn.svg
    │   │   ├── 024-unicorn.svg
    │   │   ├── 025-unicorn.svg
    │   │   ├── 026-unicorn.svg
    │   │   ├── 027-unicorn.svg
    │   │   ├── 028-unicorn.svg
    │   │   ├── 029-unicorn.svg
    │   │   ├── 030-unicorn.svg
    │   │   ├── 031-unicorn.svg
    │   │   ├── 032-rubber ring.svg
    │   │   ├── 033-unicorn.svg
    │   │   ├── 034-headband.svg
    │   │   ├── 035-unicorn.svg
    │   │   ├── 036-unicorn.svg
    │   │   ├── 037-cat.svg
    │   │   ├── 038-unicorn.svg
    │   │   ├── 039-unicorn.svg
    │   │   ├── 040-unicorn.svg
    │   │   ├── 041-unicorn.svg
    │   │   ├── 042-unicorn.svg
    │   │   ├── 043-unicorn.svg
    │   │   ├── 044-unicorn.svg
    │   │   ├── 045-unicorn.svg
    │   │   ├── 046-unicorn.svg
    │   │   ├── 047-unicorn.svg
    │   │   ├── 048-origami.svg
    │   │   ├── 049-unicorn.svg
    │   │   ├── 050-unicorn.svg
    │   │   └── README.md
    │   ├── CNAME
    │   ├── favicon.ico
    │   ├── favicon.png
    │   ├── home_bg.png
    │   └── pro_icon.svg
    ├── resources
    │   ├── asset-management
    │   │   ├── application.properties
    │   │   ├── log4j2.properties
    │   │   └── persistence.template.properties
    │   ├── auth-service
    │   │   ├── application.properties
    │   │   ├── cas.template.properties
    │   │   ├── init.template.yml
    │   │   ├── log4j2.properties
    │   │   └── persistence.template.properties
    │   ├── resource-centre
    │   │   ├── application.properties
    │   │   ├── log4j2.properties
    │   │   └── persistence.template.yml
    │   ├── Makefile
    │   ├── README.md
    │   ├── gateway.env
    │   ├── go.env
    │   ├── java.env
    │   ├── lura.env
    │   ├── nodejs.env
    │   ├── py.env
    │   ├── rust.env
    │   ├── secret.template.env
    │   └── web.env
    ├── server-nodejs
    │   ├── collection
    │   │   ├── collection.module.ts
    │   │   ├── collection.service.ts
    │   │   ├── database.controller.ts
    │   │   ├── fileCyberbrick.middleware.ts
    │   │   ├── fileManager.middleware.ts
    │   │   ├── fileManager.service.ts
    │   │   ├── home.controller.ts
    │   │   ├── misc.controller.ts
    │   │   └── upload.controller.ts
    │   ├── gallery
    │   │   ├── controller
    │   │   │   ├── author.controller.ts
    │   │   │   ├── category.controller.ts
    │   │   │   ├── content.controller.ts
    │   │   │   ├── dashboard.controller.ts
    │   │   │   ├── element.controller.ts
    │   │   │   ├── flexContent.controller.ts
    │   │   │   ├── index.ts
    │   │   │   ├── mark.controller.ts
    │   │   │   ├── record.controller.ts
    │   │   │   ├── storage.controller.ts
    │   │   │   ├── tag.controller.ts
    │   │   │   └── template.controller.ts
    │   │   ├── dto
    │   │   │   ├── author.dto.ts
    │   │   │   ├── category.dto.ts
    │   │   │   ├── dashboard.dto.ts
    │   │   │   ├── index.ts
    │   │   │   ├── read.dto.ts
    │   │   │   └── template.dto.ts
    │   │   ├── entity
    │   │   │   ├── author.entity.ts
    │   │   │   ├── category.entity.ts
    │   │   │   ├── content.entity.ts
    │   │   │   ├── dashboard.entity.ts
    │   │   │   ├── element.entity.ts
    │   │   │   ├── flexContent.entity.ts
    │   │   │   ├── index.ts
    │   │   │   ├── mark.entity.ts
    │   │   │   ├── operationRecord.entity.ts
    │   │   │   ├── storage.entity.ts
    │   │   │   ├── tag.entity.ts
    │   │   │   └── template.entity.ts
    │   │   ├── pipe
    │   │   │   ├── array.pipe.ts
    │   │   │   ├── category.pipe.ts
    │   │   │   ├── dashboard.pipe.ts
    │   │   │   ├── index.ts
    │   │   │   ├── read.pipe.ts
    │   │   │   └── template.pipe.ts
    │   │   ├── provider
    │   │   │   ├── auth.service.ts
    │   │   │   ├── author.service.ts
    │   │   │   ├── category.service.ts
    │   │   │   ├── content.service.ts
    │   │   │   ├── contentMongo.service.ts
    │   │   │   ├── dashboard.service.ts
    │   │   │   ├── element.service.ts
    │   │   │   ├── flexContent.service.ts
    │   │   │   ├── index.ts
    │   │   │   ├── mark.service.ts
    │   │   │   ├── record.service.ts
    │   │   │   ├── storage.service.ts
    │   │   │   ├── tag.service.ts
    │   │   │   └── template.service.ts
    │   │   ├── subscriber
    │   │   │   ├── DynamicConnections.ts
    │   │   │   ├── index.ts
    │   │   │   └── storage.subscriber.ts
    │   │   ├── common.ts
    │   │   └── gallery.module.ts
    │   ├── inn
    │   │   ├── controller
    │   │   │   ├── index.ts
    │   │   │   ├── tag.controller.ts
    │   │   │   └── update.controller.ts
    │   │   ├── entity
    │   │   │   ├── index.ts
    │   │   │   ├── tag.entity.ts
    │   │   │   └── update.entity.ts
    │   │   ├── provider
    │   │   │   ├── index.ts
    │   │   │   ├── tag.service.ts
    │   │   │   └── update.service.ts
    │   │   ├── common.ts
    │   │   └── inn.module.ts
    │   ├── tests
    │   │   ├── crypto_test.js
    │   │   └── mongo_test.js
    │   ├── README.md
    │   ├── app.controller.ts
    │   ├── app.module.ts
    │   ├── app.service.ts
    │   ├── config.ts
    │   ├── main.ts
    │   ├── typings.d.ts
    │   └── utils.ts
    ├── server-py
    │   ├── app
    │   │   ├── main
    │   │   │   ├── controller
    │   │   │   │   ├── __init__.py
    │   │   │   │   ├── abstract_controller.py
    │   │   │   │   ├── config_view.py
    │   │   │   │   ├── database_manipulation.py
    │   │   │   │   ├── file_upload.py
    │   │   │   │   └── util.py
    │   │   │   ├── model
    │   │   │   │   ├── __init__.py
    │   │   │   │   └── storage.py
    │   │   │   ├── provider
    │   │   │   │   ├── __init__.py
    │   │   │   │   ├── database_manipulation.py
    │   │   │   │   ├── file_upload.py
    │   │   │   │   └── util.py
    │   │   │   ├── util
    │   │   │   │   ├── __init__.py
    │   │   │   │   └── sql_loader.py
    │   │   │   ├── __init__.py
    │   │   │   └── config.py
    │   │   ├── __init__.py
    │   │   └── creator.py
    │   ├── spec
    │   │   ├── core_test.py
    │   │   └── sa_test.py
    │   ├── README.md
    │   ├── requirements.txt
    │   └── wsgi.py
    ├── server-web
    │   ├── README.md
    │   ├── go.mod
    │   ├── go.sum
    │   └── main.go
    ├── ubiquitous-api-gateway
    │   ├── config
    │   │   ├── partials
    │   │   ├── settings
    │   │   │   ├── dev
    │   │   │   └── prod
    │   │   └── templates
    │   ├── Makefile
    │   ├── README.md
    │   ├── go.mod
    │   ├── go.sum
    │   ├── lura.env
    │   └── main.go
    ├── ubiquitous-asset-management
    │   ├── logs
    │   ├── src
    │   │   ├── main
    │   │   │   ├── java
    │   │   │   │   └── com
    │   │   │   │       └── github
    │   │   │   │           └── jacobbishopxy
    │   │   │   │               └── ubiquitousassetmanagement
    │   │   │   │                   ├── config
    │   │   │   │                   │   └── DbConfig.java
    │   │   │   │                   ├── portfolio
    │   │   │   │                   │   ├── controller
    │   │   │   │                   │   │   ├── AdjustmentInfoController.java
    │   │   │   │                   │   │   ├── AdjustmentRecordController.java
    │   │   │   │                   │   │   ├── BenchmarkController.java
    │   │   │   │                   │   │   ├── ConstituentController.java
    │   │   │   │                   │   │   ├── PactController.java
    │   │   │   │                   │   │   └── PortfolioController.java
    │   │   │   │                   │   ├── domain
    │   │   │   │                   │   │   ├── obj
    │   │   │   │                   │   │   │   ├── AdjustmentOperation.java
    │   │   │   │                   │   │   │   └── AdjustmentOperationPgEnum.java
    │   │   │   │                   │   │   ├── AccumulatedPerformance.java
    │   │   │   │                   │   │   ├── AdjustmentInfo.java
    │   │   │   │                   │   │   ├── AdjustmentRecord.java
    │   │   │   │                   │   │   ├── Benchmark.java
    │   │   │   │                   │   │   ├── Constituent.java
    │   │   │   │                   │   │   ├── Pact.java
    │   │   │   │                   │   │   └── Performance.java
    │   │   │   │                   │   ├── dto
    │   │   │   │                   │   │   ├── portfolioActions
    │   │   │   │                   │   │   │   └── SettlePortfolio.java
    │   │   │   │                   │   │   ├── AdjustmentInfoUpdate.java
    │   │   │   │                   │   │   ├── BenchmarkInput.java
    │   │   │   │                   │   │   ├── BenchmarkUpdate.java
    │   │   │   │                   │   │   ├── ConstituentInput.java
    │   │   │   │                   │   │   ├── ConstituentUpdate.java
    │   │   │   │                   │   │   ├── PactInput.java
    │   │   │   │                   │   │   ├── PactOutput.java
    │   │   │   │                   │   │   ├── PortfolioDetail.java
    │   │   │   │                   │   │   └── PortfolioOverview.java
    │   │   │   │                   │   ├── repository
    │   │   │   │                   │   │   ├── AccumulatedPerformanceRepository.java
    │   │   │   │                   │   │   ├── AdjustmentInfoRepository.java
    │   │   │   │                   │   │   ├── AdjustmentRecordRepository.java
    │   │   │   │                   │   │   ├── BenchmarkRepository.java
    │   │   │   │                   │   │   ├── ConstituentRepository.java
    │   │   │   │                   │   │   ├── PactRepository.java
    │   │   │   │                   │   │   └── PerformanceRepository.java
    │   │   │   │                   │   ├── service
    │   │   │   │                   │   │   ├── helper
    │   │   │   │                   │   │   │   ├── PortfolioAdjustmentHelper.java
    │   │   │   │                   │   │   │   └── PortfolioCalculationHelper.java
    │   │   │   │                   │   │   ├── specifications
    │   │   │   │                   │   │   ├── AdjustmentInfoService.java
    │   │   │   │                   │   │   ├── AdjustmentRecordService.java
    │   │   │   │                   │   │   ├── BenchmarkService.java
    │   │   │   │                   │   │   ├── ConstituentService.java
    │   │   │   │                   │   │   ├── PactService.java
    │   │   │   │                   │   │   ├── PerformanceService.java
    │   │   │   │                   │   │   ├── PortfolioService.java
    │   │   │   │                   │   │   ├── RecalculateService.java
    │   │   │   │                   │   │   └── ValidationService.java
    │   │   │   │                   │   └── PortfolioConstants.java
    │   │   │   │                   ├── promotion
    │   │   │   │                   │   ├── controller
    │   │   │   │                   │   │   ├── PromotionPactController.java
    │   │   │   │                   │   │   ├── PromotionRecordController.java
    │   │   │   │                   │   │   └── PromotionStatisticController.java
    │   │   │   │                   │   ├── domain
    │   │   │   │                   │   │   ├── obj
    │   │   │   │                   │   │   │   ├── PerformanceScore.java
    │   │   │   │                   │   │   │   └── TradeDirection.java
    │   │   │   │                   │   │   ├── PromotionPact.java
    │   │   │   │                   │   │   ├── PromotionRecord.java
    │   │   │   │                   │   │   └── PromotionStatistic.java
    │   │   │   │                   │   ├── dto
    │   │   │   │                   │   │   ├── DateRange.java
    │   │   │   │                   │   │   ├── IntegerRange.java
    │   │   │   │                   │   │   ├── PromotionRecordInput.java
    │   │   │   │                   │   │   ├── PromotionRecordOutput.java
    │   │   │   │                   │   │   ├── PromotionRecordSearch.java
    │   │   │   │                   │   │   ├── PromotionStatisticOutput.java
    │   │   │   │                   │   │   └── SortDirection.java
    │   │   │   │                   │   ├── repository
    │   │   │   │                   │   │   ├── PromotionPactRepository.java
    │   │   │   │                   │   │   ├── PromotionRecordRepository.java
    │   │   │   │                   │   │   └── PromotionStatisticRepository.java
    │   │   │   │                   │   ├── service
    │   │   │   │                   │   │   ├── helper
    │   │   │   │                   │   │   │   └── PromotionCalculationHelper.java
    │   │   │   │                   │   │   ├── specifications
    │   │   │   │                   │   │   │   └── PromotionRecordSpecification.java
    │   │   │   │                   │   │   ├── PromotionPactService.java
    │   │   │   │                   │   │   ├── PromotionRecordService.java
    │   │   │   │                   │   │   └── PromotionStatisticService.java
    │   │   │   │                   │   └── PromotionConstants.java
    │   │   │   │                   ├── utility
    │   │   │   │                   │   ├── controller
    │   │   │   │                   │   │   ├── IndustryInfoController.java
    │   │   │   │                   │   │   └── PromoterController.java
    │   │   │   │                   │   ├── domain
    │   │   │   │                   │   │   ├── obj
    │   │   │   │                   │   │   │   ├── Role.java
    │   │   │   │                   │   │   │   └── RolePostgresEnumType.java
    │   │   │   │                   │   │   ├── IndustryInfo.java
    │   │   │   │                   │   │   └── Promoter.java
    │   │   │   │                   │   ├── repository
    │   │   │   │                   │   │   ├── IndustryInfoRepository.java
    │   │   │   │                   │   │   └── PromoterRepository.java
    │   │   │   │                   │   └── service
    │   │   │   │                   │       ├── IndustryInfoService.java
    │   │   │   │                   │       └── PromoterService.java
    │   │   │   │                   ├── Constants.java
    │   │   │   │                   └── UbiquitousAssetManagementApplication.java
    │   │   │   └── resources
    │   │   │       ├── application.properties
    │   │   │       ├── log4j2.properties
    │   │   │       └── persistence.properties
    │   │   └── test
    │   │       └── java
    │   │           └── com
    │   │               └── github
    │   │                   └── jacobbishopxy
    │   │                       └── ubiquitousassetmanagement
    │   │                           ├── DateRangeTests.java
    │   │                           ├── GuavaTests.java
    │   │                           └── UbiquitousAssetManagementApplicationTests.java
    │   ├── Makefile
    │   ├── README.md
    │   ├── mvnw
    │   ├── mvnw.cmd
    │   └── pom.xml
    ├── ubiquitous-auth
    │   ├── logs
    │   ├── src
    │   │   ├── main
    │   │   │   ├── java
    │   │   │   │   └── com
    │   │   │   │       └── github
    │   │   │   │           └── jacobbishopxy
    │   │   │   │               └── ubiquitousauth
    │   │   │   │                   ├── config
    │   │   │   │                   │   ├── CasConfig.java
    │   │   │   │                   │   ├── DatabaseConfig.java
    │   │   │   │                   │   ├── InitDataConfig.java
    │   │   │   │                   │   ├── InitDataLoader.java
    │   │   │   │                   │   ├── WebSecurityConfig.java
    │   │   │   │                   │   └── YamlPropertySourceFactory.java
    │   │   │   │                   ├── controller
    │   │   │   │                   │   ├── InformationController.java
    │   │   │   │                   │   ├── RegistrationController.java
    │   │   │   │                   │   └── WelcomeController.java
    │   │   │   │                   ├── domain
    │   │   │   │                   │   ├── UserAccount.java
    │   │   │   │                   │   ├── UserPrivilege.java
    │   │   │   │                   │   └── UserRole.java
    │   │   │   │                   ├── dto
    │   │   │   │                   │   ├── FlattenedUserAccount.java
    │   │   │   │                   │   ├── SimpleUserPrivilege.java
    │   │   │   │                   │   ├── SimpleUserRole.java
    │   │   │   │                   │   ├── UserAccountDto.java
    │   │   │   │                   │   ├── UserPrivilegeDto.java
    │   │   │   │                   │   └── UserRoleDto.java
    │   │   │   │                   ├── repository
    │   │   │   │                   │   ├── UserAccountRepo.java
    │   │   │   │                   │   ├── UserPrivilegeRepo.java
    │   │   │   │                   │   └── UserRoleRepo.java
    │   │   │   │                   ├── security
    │   │   │   │                   │   ├── CustomCasService.java
    │   │   │   │                   │   ├── CustomUserDetails.java
    │   │   │   │                   │   └── CustomUserDetailsService.java
    │   │   │   │                   ├── service
    │   │   │   │                   │   ├── AuthUtils.java
    │   │   │   │                   │   ├── RegistrationService.java
    │   │   │   │                   │   └── ValidationService.java
    │   │   │   │                   ├── Constants.java
    │   │   │   │                   └── UbiquitousAuthApplication.java
    │   │   │   └── resources
    │   │   │       ├── application.properties
    │   │   │       ├── cas.template
    │   │   │       ├── init.yml
    │   │   │       ├── log4j2.properties
    │   │   │       └── persistence.properties
    │   │   └── test
    │   │       └── java
    │   │           └── com
    │   │               └── github
    │   │                   └── jacobbishopxy
    │   │                       └── ubiquitousauth
    │   │                           ├── DevTest.java
    │   │                           ├── InitConfigurationTest.java
    │   │                           ├── UbiquitousAuthApplicationTests.java
    │   │                           ├── ValidationTest.java
    │   │                           └── WebClientTestConfiguration.java
    │   ├── Makefile
    │   ├── mvnw
    │   ├── mvnw.cmd
    │   └── pom.xml
    ├── ubiquitous-fs-server
    │   ├── cmd
    │   │   └── serve
    │   │       └── main.go
    │   ├── files
    │   │   ├── Info.go
    │   │   ├── file_md5.go
    │   │   └── util.go
    │   ├── static
    │   │   └── template.html
    │   ├── Makefile
    │   ├── README.md
    │   ├── go.mod
    │   └── go.sum
    ├── ubiquitous-resource-centre
    │   ├── logs
    │   ├── src
    │   │   ├── main
    │   │   │   ├── java
    │   │   │   │   └── com
    │   │   │   │       └── github
    │   │   │   │           └── jacobbishopxy
    │   │   │   │               └── ubiquitousresourcecentre
    │   │   │   │                   ├── config
    │   │   │   │                   │   ├── DbConfig.java
    │   │   │   │                   │   ├── DbConnections.java
    │   │   │   │                   │   └── YamlPropertySourceFactory.java
    │   │   │   │                   ├── controller
    │   │   │   │                   │   └── FileController.java
    │   │   │   │                   ├── domain
    │   │   │   │                   │   └── SimpleFile.java
    │   │   │   │                   ├── dto
    │   │   │   │                   ├── repository
    │   │   │   │                   ├── service
    │   │   │   │                   │   └── FileService.java
    │   │   │   │                   └── UbiquitousResourceCentreApplication.java
    │   │   │   └── resources
    │   │   │       ├── application.properties
    │   │   │       ├── log4j2.properties
    │   │   │       └── persistence.yml
    │   │   └── test
    │   │       └── java
    │   │           └── com
    │   │               └── github
    │   │                   └── jacobbishopxy
    │   │                       └── ubiquitousresourcecentre
    │   │                           └── UbiquitousResourceCentreApplicationTests.java
    │   ├── Makefile
    │   ├── README.md
    │   ├── mvnw
    │   ├── mvnw.cmd
    │   └── pom.xml
    ├── ubiquitous-tg-server
    │   └── README.md
    ├── web
    │   └── README.md
    ├── LICENSE
    ├── Makefile
    ├── README.md
    ├── clean.sh
    ├── setup.sh
    └── start.sh
