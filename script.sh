echo "Inside Shell Script yay !" ;
cd BackEnd ;
ls -la ;
git status ;
git pull ;
git status ;
docker-compose down ;
docker build -t backend .
docker-compose up --build -d ;
docker ps -a ;
docker image prune -f ;
docker ps -a ;