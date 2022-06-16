import React, { useState } from 'react'
import { useNavigate } from 'react-router';

export default function Login({ name, setName }) {

  const navigate = useNavigate();

  const [password, setPassword] = useState(''); 


  function handleSubmit(event) {
    event.preventDefault();

    //TODO: add to database
    console.log(name);
    console.log(password);

    navigate('/home')
  }

  return (
      <>
      <h1>Please enter your name and a password to revisit your session</h1>
      <form onSubmit={handleSubmit}>
          <div>
              <label>Name</label>
                <input
                id="name"
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                />
          </div>
          <div>
                <label>Password</label>
                <input
                id="password"
                type="text"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                />
          </div>

          <button type="submit">Submit</button>

      </form>
      </>
    
  )
}
