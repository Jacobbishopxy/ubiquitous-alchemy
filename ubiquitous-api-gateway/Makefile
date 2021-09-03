
# dev run
dev:
	go run .

# CAUTIOUS! May override your current env config
env-setup:
	cp lura.template.env lura.env
	cp ./config/settings/prod/host.template.json ./config/settings/prod/host.json
	cp ./config/settings/prod/service.template.json ./config/settings/prod/service.json

.PHONY: dev env-setup
