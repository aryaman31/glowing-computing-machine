import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Home';
import BookAppointmentPage from './BookAppointmentPage';
import ViewAppointmentPage from './ViewAppointmentPage';
import YourAppointmentPage from './YourAppointmentPage';
import AppointmentNote from './AppointmentNote';
import Login from './Login';

function App() {

  const [name, setName] = useState('');

  const [upcomings, setUpcoming] = useState([]);

  //get all appointments from database
  const [allAppoints, setAllAppoints] = useState([]) 

  return (
    <Router>
      <div className='app'>
        <div className='content'>
          <Routes>

            <Route exact path="/">
              <Route exact path='/' element={<Login name={name} setName={setName}/>}/>  
            </Route>  

            <Route exact path="/home">
            <Route exact path='/home' element={<Home name={name}/>}/>
            </Route>

            <Route exact path="/bookings">
              <Route exact path='/bookings' element={
                <BookAppointmentPage 
                  setUpcoming={setUpcoming} 
                  name={name} 
                  allAppoints={allAppoints} 
                  setAllAppoints={setAllAppoints} 
                />
              }/>
            </Route>

            <Route exact path="/view">
              <Route exact path='/view' element={
                <ViewAppointmentPage upcomings={upcomings} setUpcoming={setUpcoming} />
              }/>
            </Route>

            <Route exact path="/your_appointment/:aid">
              <Route exact path='/your_appointment/:aid' element={
                <YourAppointmentPage upcomings={upcomings} />
              }/>
            </Route>

            <Route exact path="/note">
              <Route exact path='/notes' element={
                <AppointmentNote/>
              }/>
            </Route>

          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
