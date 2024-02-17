build:
	@echo "Building GUI"
	cd prefo-gui && npm run build
	
	@echo "Building App"
	cd prefo-app && ./gradlew bootJar --scan --info

restart:
	docker compose down
	make build
	docker compose up -d
