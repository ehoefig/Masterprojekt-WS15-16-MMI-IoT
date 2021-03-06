var React = require('react')
  , Arrow3D = require('../shared/Arrow3D')
  , Graph = require('../shared/Graph')
  , TestNew = require('../shared/TestNew')
  , rabbit = require('../../js/rabbit')
  , DynamoArrow = require('../shared/DynamoArrow')
  , DynamoGraph = require('../shared/DynamoGraph')
  , TopBar = require('../shared/TopBar');

var EditSensor = require('./EditSensor')
  , DeleteSensor = require('./DeleteSensor');

var SingleSensor = React.createClass({

  propTypes: {
    user: React.PropTypes.object,
    sensor: React.PropTypes.object
  },

  getInitialState: function() {
    return {
      isLive: false,
      connection: {},
      cQ: [0, 0, 0, 0],
      cA: null,
      fields: [
        ['id', 'ID'],
        ['location', 'Location'],
        ['creationDate', 'Creation Date'],
        ['types','Sensor Types'],
        ['owner', 'Owner'],
        ['attachedGateway', 'Attached Gateway'],
        ['attachedCluster', 'Attached Cluster']
      ],
      data: [],
      editSensor: false,
      deleteSensor: false
    };
  },

  componentDidMount: function() {
    //var i = 0;
    //var max = this.state.data.length - 1;
    //var that = this;
    //setInterval(function() {
      //if (i === max) i =0;
      //var date = that.state.data[i].time;
      ////console.log('date', date);
      //that.setState({
        //cQ: that.state.data[i].orientation,
        //cA: {acceleration: that.state.data[i].acceleration, time: date}
      //});
      //i++;
    //}, 13);
  },

  handleSwitch: function() {
    var isLive = !this.state.isLive;
    console.log('is it live:', isLive);
    if (isLive) {
      var connection = this.connect();
    } else {
      this.disconnect();
    }
    this.setState({isLive: isLive, connection: connection});
  },

  test: function(body) {
    if (this.state.myInterval) clearInterval(this.state.myInterval);
    var that = this;
    var max = body.length
    var hz = Math.floor(1000/max) - 2;
    var i = 0;
    //var myInterval = setInterval(function() {
      //if (i == max) {
        //i = 0;
        //clearInterval(myInterval);
      //}
      //var date = body[i].time;
      //that.setState({
        //cQ: body[i].orientation,
        //cA: {acceleration: body[i].acceleration, time: date}
      //});
      //i++;
    //}, hz);
    this.setState({data: body}, function() {
      console.log('neue daten');
      var myInterval = setInterval(function() {
        if (i++ == max - 1) {
          i = 0;
          clearInterval(myInterval);
          return;
        }
        if (!that.state.data[i]) return;
        var date = that.state.data[i].time;
        var maxDate = that.state.data[that.state.data.length - 1].time;
        that.setState({
          cQ: that.state.data[i].orientation,
          cA: {value: that.state.data[i].acceleration, time: date},
          maxDate: {time: maxDate},
          myInterval: myInterval
        });
      }, hz);
    });
  },

  connect: function() {
    var that = this;
    var rabbitClient = rabbit.connect();
    rabbitClient.connect('guest', 'guest', function() {
      console.log('connect');
      var subscription = rabbitClient
        .subscribe('/exchange/friss_exch/' + that.props.sensor.id, function(msg) {
           //console.log(JSON.parse(msg.body));
           that.test(JSON.parse(msg.body));
      });
    });
    return rabbitClient;
  },

  disconnect: function() {
    this.state.connection.disconnect(function() {
      console.log('disconnect');
    });
  },

  renderFields: function(){
    var that = this;
    return this.state.fields.map(function(field){
      var value;
      var caption;
      var id;
      if(Array.isArray(field)){
        id = field[0];
        value = that.props.sensor[field[0]] || 'missing';
        caption = field[1];
      }else{
        value = that.props.sensor[field] || 'missing';
        caption = field;
        id = field;
      }
      var text = value;
      if(id === 'creationDate'){
        var date = new Date(text);
        text = date.toUTCString();
      }else if(id === 'types'){
        text = that.props.sensor[field[0]].join(', ');
      }
      return(
        <tr key={caption}>
          <td>{caption}</td>
          <td>{id === 'owner' ? <a href={"/user/" + text}>{text}</a> : text}</td>
        </tr>
      );
    });
  },

  //renderGraph: function(){
    //return (
      //<div className='row'>
        //<div className='large-12 columns' style={{border: '1px solid black',height:300}}>super meger krasse d3 Grafik</div>
      //</div>);
  //},

  handleEditSensor: function(){
    this.setState({editSensor: !this.state.editSensor})
  },

  handleDeleteSensor: function(){
    this.setState({deleteSensor: !this.state.deleteSensor});
  },

  render: function() {
    var displayStyle = this.state.editSensor || this.state.deleteSensor
                        ? 'block' : 'none';
    return (
      <div>
        <TopBar user={this.props.user} activePage='sensors' />
        <div style={{marginTop: 25}}>
          <div className='row column' style={{float: 'none'}}>
            <div className='callout'>
              <div className='row' style={{marginRight: 0}}>
                <div className='small-8 columns'>
                  <h3>{this.props.sensor.name}</h3>
                </div>
                <div className='small-4 columns' style={{textAlign: 'right', paddingRight: 0}}>
                  <div className='button' onClick={this.handleEditSensor}>
                    Edit
                  </div>
                  <div className='button alert'
                    style={{marginRight:0}}
                    onClick={this.handleDeleteSensor}>
                    Delete
                  </div>
                </div>
              </div>
                <table style={{width: '100%'}}>
                  <tbody style={{borderWidth: 0}}>
                    {this.renderFields()}
                  </tbody>
                </table>
            </div>
          </div>
          <div className='row column' style={{float:'none'}}>
            <div className='callout'>
              <div style={{textAlign: 'center'}}>
                <p style={{marginBottom: 0}}>Show Live Data</p>
                <div className="switch large">
                  <input className="switch-input"
                    id="yes-no"
                    type="checkbox"
                    onClick={this.handleSwitch}
                    name="exampleSwitch"/>
                  <label className="switch-paddle" htmlFor="yes-no">
                    <span className="show-for-sr"></span>
                    <span className="switch-active" aria-hidden="true">Yes</span>
                    <span className="switch-inactive" aria-hidden="true">No</span>
                  </label>
                </div>
              </div>
              <div className='row'>
                <div className='large-6 columns'>
                  <h5>Orientation</h5>
                  { this.state.isLive
                    ? <Arrow3D quad={this.state.cQ}/>
                    : <DynamoArrow />
                  }
                </div>
                <div className='large-6 columns'>
                  <h5>Acceleration</h5>
                  { this.state.isLive
                    ? <TestNew value={this.state.cA} maxDate={this.state.maxDate}/>
                    : <DynamoGraph />
                  }
                </div>
              </div>
            </div>
          </div>
        </div>

        {this.state.editSensor
          ? <EditSensor cancelCallback={this.handleEditSensor}
            sensor={this.props.sensor}/>
          : null}
        {this.state.deleteSensor ? <DeleteSensor cancelCallback={this.handleDeleteSensor} sensor={this.props.sensor}/> : null}
          <div className='background-area'
            style={{display: displayStyle}}>
          </div>
      </div>
    );
  }
});

module.exports = SingleSensor;
