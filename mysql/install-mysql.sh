minikube start
minikube status
#install mysql by helm
helm install mysql-release bitnami/mysql
#after install run dry-run to test the database
helm install mysql-release bitnami/mysql --debug --dry-run >mysql-dry-run.log
#get the password of root db user
kubectl get secret --namespace default mysql-release-mysql -o jsonpath="{.data.mysql-root-password}" | base64 --decode
#check the mysql pod is up
kubectl get pods -w --namespace default
#set the password into variable
MYSQL_ROOT_PASSWORD=$(kubectl get secret --namespace default mysql-release -o jsonpath="{.data.mysql-root-password}" | base64 -d)
#show the password in variable
echo $MYSQL_ROOT_PASSWORD
#deploy the mysql pod
kubectl run mysql-release-client --rm --tty -i --restart='Never' --image  docker.io/bitnami/mysql:8.0.36-debian-12-r8 --namespace default --env MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD --command -- bash

mysql -h mysql-release.default.svc.cluster.local -uroot -p"$MYSQL_ROOT_PASSWORD"
