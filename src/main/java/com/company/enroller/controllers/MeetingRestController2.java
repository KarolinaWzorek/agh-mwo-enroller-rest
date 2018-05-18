package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/meeting")
public class MeetingRestController2 {

	@Autowired
	MeetingService meetingService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
	     Meeting meeting = meetingService.findByLogin(id);
	     if (meeting == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	 }
		

		
		@RequestMapping(value = "", method = RequestMethod.POST)
		 public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting){
			
			if (meetingService.findByLogin(meeting. getId()) !=null){
				return new ResponseEntity(
						"Unable to create. A participant with login " +
				meeting. getId() +" already exist.",
				HttpStatus.CONFLICT);
			}
			meetingService.create(meeting);
			return new ResponseEntity<Meeting  >(meeting, HttpStatus.CREATED );
		}
		
		
		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		 public ResponseEntity<?> deleteMeeting( @PathVariable("id")  long id){
		     Meeting meeting = meetingService.findByLogin(id);
		
			if (meeting==null){
				return new ResponseEntity(
				HttpStatus.NOT_FOUND);
			}
			meetingService.delete(meeting);
			return new ResponseEntity<Meeting >(HttpStatus.OK );
		}
		
		
		@RequestMapping(value = "{id}", method = RequestMethod.PUT)
		public ResponseEntity<?> updateMeeting(@PathVariable("id") long id, @RequestBody Meeting meeting) {
		
		Meeting newMeeting = meetingService.findByLogin(id);
	
		if (newMeeting == null) {
				return new ResponseEntity(new Error("Unable to update" + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		
			newMeeting.setTitle(meeting.getTitle());
			newMeeting.setDescription(meeting.getDescription());
			newMeeting.setDate(meeting.getDate());
		
				meetingService.update(newMeeting);
				return new ResponseEntity(HttpStatus.OK);
			}
		
		

		@RequestMapping(value = "{id}/addparticipant", method = RequestMethod.GET)
		public ResponseEntity<?> addParticipant(@PathVariable("id") long id) {
		
		Meeting newmeeting = meetingService.findByLogin(id);
	
		if (newmeeting == null) {
				return new ResponseEntity(new Error("Unable to add" + id),
					HttpStatus.NOT_FOUND);
		}
			return new ResponseEntity(newmeeting.getParticipants(),HttpStatus.OK);
			}
		
				}
