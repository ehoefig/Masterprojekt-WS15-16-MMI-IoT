var request = require('superagent')
  , config = require('../plugins/config')
  , Sensor = require('./sensor')
  , Bluebird = require('bluebird');

var dynamodb = require('../plugins/dynamodb');

module.exports = me = {};

var endpoint = config.rest_endpoint;

me.getAll = (user) => {
  return new Bluebird((resolve, reject) => {
    request
      .get(endpoint + '/cluster')
      .auth(user.Username, user.password)
      .end((err, res) => {
        if (err) reject(err);
        resolve(res.body);
      });
  });
};

me.getOne = (user, id) => {
  return new Bluebird((resolve, reject) => {
    request
      .get(endpoint + '/cluster/' + id)
      .auth(user.Username, user.password)
      .end((err, res) => {
        if (err) reject(err);
        resolve(res.body);
      });
  });
};

me.getSensors = (user, sensorList) => {
  return new Bluebird((resolve, reject) => {
    var promiseArray = [];
    sensorList.forEach(id => {
      promiseArray.push(Sensor.getOne(user, id));
    });
    Bluebird.all(promiseArray).then(sensors => {
      resolve(sensors);
    });
  });
};

me.update = (user, clusterToUpdate) => {
  return new Bluebird((resolve, reject) => {
    request
      .put(endpoint + '/cluster/' + clusterToUpdate.username)
      .send(clusterToUpdate)
      .auth(user.Username, user.password)
      .end((err, res) => {
        if (err) reject(err);
        resolve(res);
      });
  });
};

me.delete = (user, clusterToDelete) => {
  return new Bluebird((resolve, reject) => {
    request
      .delete(endpoint + '/cluster/' + clusterToDelete)
      .auth(user.Username, user.password)
      .end((err, res) => {
        if (err) reject(err);
        resolve(res);
      });
  });
};

me.create = (user, clusterToCreate) => {
  return new Bluebird((resolve, reject) => {
    request
      .post(endpoint + '/cluster')
      .send(clusterToCreate)
      .auth(user.Username, user.password)
      .end((err, res) => {
        if (err) reject(err);
        resolve(res);
      });
  });
};

