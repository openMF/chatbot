# This files contains your custom actions which can be used to run
# custom Python code.
#
# See this guide on how to implement these action:
# https://rasa.com/docs/rasa/custom-actions


# This is a simple example for a custom action which utters "Hello World!"

# from typing import Any, Text, Dict, List
#
# from rasa_sdk import Action, Tracker
# from rasa_sdk.executor import CollectingDispatcher
#
# class ExtractCredentials(Action):
#     def name(self) -> Text:
#         return "action_extract_credentials"
#
#     def run(self, dispatcher: CollectingDispatcher,
#             tracker: Tracker,
#             domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
#
#         # Get the user input
#         user_input = tracker.latest_message.get('text')
#
#         # Extract username and password from the user input
#         if user_input.startswith("login:"):
#             credentials = user_input[len("login:"):].split()
#             if len(credentials) == 2:
#                 username, password = credentials
#                 # Set the slot values
#                 return [
#                     SlotSet("username", username),
#                     SlotSet("password", password)
#                 ]
#             else:
#                 dispatcher.utter_message(text="Invalid input. Please provide a username and password.")
#         else:
#             dispatcher.utter_message(text="Invalid input. Please provide a login command.")
#
#         return []



# class ActionHelloWorld(Action):
#
#     def name(self) -> Text:
#         return "action_hello_world"
#
#     def run(self, dispatcher: CollectingDispatcher,
#             tracker: Tracker,
#             domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
#
#         dispatcher.utter_message(text="Hello World!")
#
#         return []
