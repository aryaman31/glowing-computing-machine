import React, { useState } from 'react'
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';

export default function Login({ setPatient }) {

  const navigate = useNavigate();

  const [password, setPassword] = useState(''); 
  const [nhsNumber, setNhsNumber] = useState(0);


  function handleSubmit(event) {
    event.preventDefault();

      fetch(`/api/patient?id=${nhsNumber}`)
      .then(response => response.json())
      .then(data => {
        setPatient(data);
        console.log(data)
      }).catch(() => {
        console.log("Error couldnt find patient");
      });
      
    navigate('/home')
  }

  return (
      <div className='fit-to-page column center'>
        <h1>Login</h1>
        <form onSubmit={handleSubmit} className='login-form center'>
            <div className='login-form-field'>
                <label>NHS Id</label>
                  <input
                  id="name"
                  type="text"
                  // defaultValue={null}
                  value={nhsNumber}
                  onChange={(e) => setNhsNumber(e.target.value)}
                  />
            </div>
            <div className='login-form-field'>
                  <label>Password</label>
                  <input
                  id="password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  />
            </div>

            <button className='btn login-form-field' type="submit">Submit</button>

            <Link to="/" className='btn'>Back</Link>


        </form>
      </div>
    
  )
}
