build-all:
	@echo "Building GUI"
	cd prefo-gui && npm run build
	
	@echo "Building App"
	cd prefo-app && ./gradlew build
	
