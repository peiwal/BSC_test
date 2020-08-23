package petrov.ivan.bsc.providers

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import petrov.ivan.bsc.data.Party
import petrov.ivan.bsc.data.Person

class ContactsProvider(val context: Context) {
    private val contentResolver: ContentResolver = context.contentResolver

    fun updatePersonInfo(listParty: List<Party>): List<Party> {
        val cursorPhoneList: Cursor? = contentResolver.query(CONTENT_URI, null, null, null, null)

        cursorPhoneList?.let {
            if (cursorPhoneList.getCount() > 0) {
                while (cursorPhoneList.moveToNext()) {
                    val contactId = cursorPhoneList.getString(cursorPhoneList.getColumnIndex(_ID))
                    val contactName = cursorPhoneList.getString(cursorPhoneList.getColumnIndex(DISPLAY_NAME))
                    val hasPhoneNumber = Integer.parseInt(cursorPhoneList.getString(cursorPhoneList.getColumnIndex(HAS_PHONE_NUMBER)))

                    // если есть телефонный номер, ищем дополнительную информацию по id
                    if (hasPhoneNumber > 0) {
                        val cursorPhoneInfo = contentResolver.query(PhoneCONTENT_URI, null,
                        Phone_CONTACT_ID + " = ?",  arrayOf(contactId), null)

                        cursorPhoneInfo?.let { cursor ->
                            while (cursor.moveToNext()) {
                                val phoneNumber = cursor.getString(cursor.getColumnIndex(NUMBER))
                                val photoStr = cursor.getString(cursor.getColumnIndex(
                                    Phone_PHOTO_URI))

                                val photoUri = if (photoStr != null)
                                    Uri.parse(photoStr)
                                else null

                                // update info
                                listParty.forEach { party ->
                                    if (comparePhone(party.inviter.phone, phoneNumber)) {
                                        updatePersonInfo(party.inviter, contactName, photoUri)
                                    }
                                    party.listOfInvitees.forEach {
                                        if (comparePhone(it.phone, phoneNumber)) {
                                            updatePersonInfo(it, contactName, photoUri)
                                        }
                                    }
                                }
                            }
                            cursorPhoneInfo.close()
                        }
                    }
                }
            }
            cursorPhoneList.close()
        }
        return listParty
    }

    private fun comparePhone(first: String, second: String): Boolean {
        return formatPhoneNumber(first) == formatPhoneNumber(second)
    }

    // fixme: это конечно плохое решение.. нужно переделать
    private fun formatPhoneNumber(phone: String): String {
        return phone.substring(1, phone.length)
            .replace(" ", "")
            .replace("-", "")
    }

    private fun updatePersonInfo(it: Person, name: String, photoUri: Uri?) {
        it.imgUri = photoUri
        it.name = name
    }

    companion object {
        private val CONTENT_URI: Uri = ContactsContract.Contacts.CONTENT_URI
        private val _ID = ContactsContract.Contacts._ID
        private val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
        private val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER

        private val PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        private val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        private val Phone_PHOTO_URI = ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        private val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
    }
}