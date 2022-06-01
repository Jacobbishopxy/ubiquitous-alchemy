ifneq (,$(wildcard .env))
    include .env
    export
endif


dev:
	./mvnw spring-boot:run

clean:
	./mvnw clean

build:
	./mvnw package

start:
	java -jar target/ubiquitous-resource-centre-${VERSION}.jar
