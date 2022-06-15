import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './Home';
import BookAppointmentPage from './booking/BookAppointmentPage';
import ViewAppointmentPage from './viewing/ViewAppointmentPage';
import YourAppointmentPage from './viewing/YourAppointmentPage';
import AppointmentNote from './viewing/AppointmentNote';
import Login from './Login';
import Test from './booking/Test';

function App() {

  const [name, setName] = useState('');

  const [upcomings, setUpcoming] = useState([]);

  //get all appointments from database
  const [allAppoints, setAllAppoints] = useState([]) 

  return (
    <Router>
      <div className='app'>
        <div className='content'>
          <Switch>

            <Route exact path="/">
              <Login name={name} setName={setName}/>  
            </Route>  

            <Route exact path="/home">
              <Home name={name}/>
            </Route>

            <Route exact path="/bookings">
              <BookAppointmentPage 
                setUpcoming={setUpcoming} 
                name={name} 
                allAppoints={allAppoints} 
                setAllAppoints={setAllAppoints} 
              />
            </Route>

            <Route exact path="/view">
              <ViewAppointmentPage upcomings={upcomings} setUpcoming={setUpcoming} />
            </Route>

            <Route exact path="/your_appointment/:aid">
              <YourAppointmentPage upcomings={upcomings} />
            </Route>

            <Route exact path="/note">
              <AppointmentNote/>
            </Route>

          </Switch>
        </div>
      </div>
    </Router>
  );
}

export default App;
