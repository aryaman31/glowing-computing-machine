import React, { Fragment } from 'react'

export default function PatientAppointment({ appoint }) {
  return (
    <>
    <p>{appoint.time}</p>
    <p>Patient name: {appoint.nhsNumber}</p>
    <p>Problem faced: {appoint.problem}</p>
    <p>Description:</p>
    {appoint.description.split('\n').map(str => 
      <Fragment key={str}>
        <p >{str}</p>
      </Fragment>
      )}

    </>
  )
}
