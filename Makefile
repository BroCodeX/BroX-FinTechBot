test: #A build of the app
	make -C fbot test

run-dist: #Launch of the program

build: #A build of the app
	make -C fbot build

clean:
	make -C fbot clean

lint: #Chech a style of code via Checkstyle
	make -C fbot lint

report: #Make a JaCoCo Report
	make -C fbot report

.PHONY: build
