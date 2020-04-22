#build image
docker build -t leventyildiz/couchbase  .

#Run as container
docker run --name couchbase -p 8091:8091  -p 8093:8093 -p 11210:11210 leventyildiz/couchbase &
sleep 10

#initialize couchbase cluster
docker exec -it couchbase /bin/bash /tmp/config.sh exit
