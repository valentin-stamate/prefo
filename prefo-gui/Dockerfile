FROM nginx:stable-alpine

WORKDIR /home/app
COPY ./dist/prefo-gui .

RUN mv ./* /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

#FROM nginx:stable-alpine
#
#RUN apk add --update nodejs npm
#
#WORKDIR /home/app
#
#COPY package.json .
#RUN npm install && npm cache clean --force
#
#COPY . .
#RUN npm run build
#RUN mv dist/prefo-gui/* /usr/share/nginx/html
#
#COPY nginx.conf /etc/nginx/nginx.conf
#
## The removing it's not working
#
#RUN rm -rf node_modules
#RUN apk del nodejs npm
#RUN rm -rf /usr/local/lib/node_modules /usr/bin/node
#RUN rm -rf ~/.nvm ~/.npm ~/.bower
