
submodule-init:
	git submodule init
	git submodule update

submodule-update:
	cd ${FILEBROWSER_SUBMODULE_PATH} && git pull

submodule-build:
	cd ${FILEBROWSER_SUBMODULE_PATH}/frontend && yarn && yarn build
	cd ${FILEBROWSER_SUBMODULE_PATH} && go build

submodule-dev:
	cd ${FILEBROWSER_SUBMODULE_PATH} ./filebrowser
