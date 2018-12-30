curl -XPUT '47.106.80.130:9200/students?pretty'
curl -XPUT  -H 'Content-type:application/json;charset=utf-8'  '47.106.80.130:9200/students/external/1?pretty' -d '{"name":"John Doe"}'

curl -XGET '47.106.80.130:9200/students/external/1?pretty'

curl '47.106.80.130:9200/students/_search?q=*&pretty'

