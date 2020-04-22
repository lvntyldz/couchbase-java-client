/entrypoint.sh couchbase-server &
sleep 5
couchbase-cli cluster-init --cluster-username=Administrator --cluster-password=a12345 --cluster-port=8091 --cluster-ramsize=768 --cluster-index-ramsize=256 --services=data,index,query,fts
sleep 5
curl -u Administrator:a12345 -v -X POST  http://127.0.0.1:8091/settings/indexes -d "storageMode=forestdb"
sleep 5
curl -u Administrator:a12345 -v -X POST http://127.0.0.1:8091/pools/default/buckets -d 'flushEnabled=1&threadsNumber=3&replicaIndex=0&replicaNumber=0&evictionPolicy=valueOnly&ramQuotaMB=256&bucketType=membase&name=default&authType=sasl&saslPassword='
sleep 5
curl -u Administrator:a12345 -v -X POST http://127.0.0.1:8091/pools/default/buckets -d 'flushEnabled=1&threadsNumber=3&replicaIndex=0&replicaNumber=0&evictionPolicy=valueOnly&ramQuotaMB=256&bucketType=membase&name=example&authType=sasl&saslPassword='
sleep 5
curl -v http://127.0.0.1:8093/query/service -d 'statement=SELECT * FROM example LIMIT 1'
sleep 5
curl -v http://127.0.0.1:8093/query/service -d 'statement=CREATE PRIMARY INDEX example_index ON example USING GSI'
