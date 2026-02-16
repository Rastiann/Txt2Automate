package automate.model

import automate.model.Puit
import java.awt.Desktop
import java.io.File

class Automate(

    val nom: String,
    val alphabet: Set<Char>,
    val etats: Set<Etat>,
    val etatInitiale: Etat,
    val etatFinale: Set<Etat>,
    val delta: Map<Pair<Char, Etat>, Etat>

) {


    fun validation(mot: String): String {
        var etatCourant: Etat = this.etatInitiale

        mot.forEachIndexed { index, c ->

            if (!this.alphabet.contains(c)) {
                return "KO : caractère '${c}' non autorisé. Alphabet autorisé : [ ${alphabet.joinToString(", ")} ]"
            }

            val etatSuivant = etatCourant.etatSuivant(c)
            println("Lecture du caractère '${c}' à la position ${index + 1} : ${etatCourant.nom} -> ${etatSuivant.nom}")
            etatCourant = etatSuivant
        }

        if (etatCourant is Puit) return "KO : vous êtes tombé dans le puits"

        return if (this.etatFinale.contains(etatCourant)) "OK : mot accepté" else "KO : mot non accepté"
    }


    // mettre dans le dotContent et le faire s'ouvrir avec des commandes en bash dans le terminal
    override fun toString(): String {

        val dotFile = this.generateDot()

        val pngFile = File("src/automate/data/png/${this.nom}.png")


        try {
            this.generatePng(dotFile, pngFile)
            Desktop.getDesktop().open(pngFile)
        } catch (e: Exception) {
            println("ERROR: ${e.message}, Impossible d'ouvir le .dot sur cet os")
        }


        return ""
    }


    private fun generateDot(): File {
        var dotContent = """
            digraph {
                rankdir=LR;
                node [shape=circle, fontname="Helvetica"];
                edge [fontname="Helvetica"];
                label="Automate ${this.nom}"
            """.trimIndent() + "\n"

        this.etats.forEach { etat ->
            if (this.etatFinale.contains(etat)) {
                dotContent += "    ${etat.nom} [shape=doublecircle];\n"
            } else {
                dotContent += "    ${etat.nom};\n"
            }
        }

        val groupDelta = this.delta.entries.groupBy { it.key.second.nom to it.value.nom }
        groupDelta.forEach { (paire, transi) ->
            val resp = transi.map { it.key.first }.joinToString(", ")
            dotContent += "    ${paire.first} -> ${paire.second} [label=\"$resp\"]\n"
        }

        dotContent += "}"

        val dotFile = File("src/automate/data/dot/${this.nom}.dot")
        dotFile.writeText(dotContent)

        return dotFile
    }


    private fun generatePng(dotFile: File, pngFile: File) {
        val os = System.getProperty("os.name")

        val dotPath = when {
            os.contains("windows", ignoreCase = true) -> "C:\\Program Files (x86)\\Graphviz\\bin\\dot.exe"

            os.contains("linux", ignoreCase = true) -> "dot"

            else -> {
                throw Error("unexpected OS :$os")
            }
        }

        val process = ProcessBuilder(dotPath, "-Tpng", dotFile.absolutePath, "-o", pngFile.absolutePath)
            .start()

        process.waitFor()
    }

    companion object {

        fun isConnexe(automate: Automate): Boolean {
            val transitions: Map<Etat, List<Etat>> = automate.delta.entries.groupBy(
                keySelector = { it.key.second },
                valueTransform = { it.value }
            )

            val visited = mutableSetOf<Etat>()
            val stack = ArrayDeque<Etat>()

            stack.add(automate.etatInitiale)

            while (stack.isNotEmpty()) {
                val current = stack.removeLast()

                if (current !in visited) {
                    visited.add(current)

                    val childrens = transitions[current].orEmpty()
                    for (children in childrens) {
                        if (children !in visited) {
                            stack.add(children)
                        }
                    }
                }
            }

            return visited.size == automate.etats.size
        }

    }


}