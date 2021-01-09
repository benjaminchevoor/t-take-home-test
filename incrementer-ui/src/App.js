import './App.css';
import React from 'react';

class IncrementingServiceClient extends React.Component {

  static serviceUrl = "http://localhost:8080/"

  state = {
    apiKey: null,
    currentVal: null
  }

  registerUser = () => {
    console.log("registering new user")

    fetch(IncrementingServiceClient.serviceUrl)
      .then(response => console.log(response.body))
      .catch(error => console.log(error));
  }

  getCurrentInteger = () => {
    if (!this.state.apiKey) {
      alert("User not registered! Please register a user first.")
      return;
    }
  }

  render() {
    return (
      <div className="incrementing-service-client">
        <div className="App">
          <header className="App-header">  
            Incrementing Integer Service!
          </header>
          <div className="App-section">
            <div className="App-section-header">
              User
            </div>
            <div className="App-section-content">
              {this.state.apiKey || "Not registered"}
            </div>
            <div className="App-section-buttons">
              <button onClick={this.registerUser}>Register user</button>
              <button>Unregister user</button>
            </div>
          </div>
          
          <div className="App-section">
            <div className="App-section-header">
              Integer
            </div>
            <div className="App-section-content">
              {this.state.currentVal || "?"}
            </div>
            <div className="App-section-buttons">
              <button onClick={this.getCurrentInteger}>Current integer</button>
              <button>Next integer</button>
              <button>Put integer</button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

class App extends React.Component {

  render() {
    return (
      <IncrementingServiceClient/>
    );
  }

}


export default App;

