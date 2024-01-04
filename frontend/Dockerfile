# Use an official Node.js image as the base image
FROM node:21.4.0

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json (or yarn.lock) to the working directory
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the remaining application code to the working directory
COPY . .

# Set the command to run the application
CMD ["npm", "start"]