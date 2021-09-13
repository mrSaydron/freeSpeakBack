push:
	docker build -t mrsaydron/free-speak .
	docker push mrsaydron/free-speak

push_dev:
	docker build -t mrsaydron/free-speak:dev -f Dockerfile_dev .
	docker push mrsaydron/free-speak:dev
